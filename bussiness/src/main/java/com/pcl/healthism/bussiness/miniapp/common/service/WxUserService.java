package com.pcl.healthism.bussiness.miniapp.common.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.pcl.healthism.bussiness.miniapp.common.entity.WxUser;
import com.pcl.healthism.dao.mapper.WxUserInfoMapper;
import com.pcl.healthism.dao.po.WxUserInfoPO;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class WxUserService {

    private static Logger logger = LoggerFactory.getLogger(WxUserService.class);
    private static ListeningExecutorService executorService = MoreExecutors
            .listeningDecorator(Executors.newFixedThreadPool(2));
    private final WxMaService wxMaService;
    private final WxUserInfoMapper wxUserInfoMapper;

    private LoadingCache<String, WxUser> users;

    @PostConstruct
    private void init() {
        users = CacheBuilder.newBuilder()
                .maximumSize(10000)
                .expireAfterAccess(2, TimeUnit.MINUTES)
                .build(new CacheLoader<String, WxUser>() {
                    public WxUser load(@Nonnull String key) {
                        return innerGetUser(key);
                    }

                    @Override
                    public ListenableFuture<WxUser> reload(String key, WxUser oldValue) throws Exception {
                        return executorService.submit(() -> {
                            WxUser user = innerGetUser(key);
                            if (user.emptyUser()) {
                                return oldValue;
                            } else {
                                updateUser(user); //更新本地微信用户数据
                            }
                            return user;
                        });
                    }
                });
    }

    public WxUser get(String openId) {
        Preconditions.checkArgument(openId != null && !openId.isEmpty());
        Preconditions.checkArgument(!openId.equals("null"));
        try {
            WxUser user = users.get(openId);
            if (user == WxUser.NULL) {
                refresh(openId);
            }
            return user;
        } catch (Exception e) {
            logger.error("get weixin user error, openId={}", openId, e);
        }
        return WxUser.NULL;
    }

    public void refresh(String openId) {
        users.refresh(openId);
    }

    private WxUser innerGetUser(String key) {
        try {
            //TODO 这里并没有实现真正获取用户信息逻辑
            WxMaUserInfo mpUser = new WxMaUserInfo();
            WxUser user = new WxUser();
            BeanUtils.copyProperties(mpUser, user);
            WxUserInfoPO infoPO = wxUserInfoMapper.query(key);
            user.setUserMobile("");
            if (infoPO != null) {
                user.setUserMobile(infoPO.getUserMobile());
            }
            return user;
        } catch (Exception e) {
            logger.error("query weixin user info error, key={}", key, e);
        }
        return WxUser.NULL ;
    }

    private void updateUser(WxUser user) {
        if (user.emptyUser()) {
            return;
        }
        WxUserInfoPO oldPO = wxUserInfoMapper.query(user.getOpenId());
        if (oldPO == null) {
            return;
        }
        WxUserInfoPO newPO = new WxUserInfoPO();
        newPO.setAddTimestampMils(oldPO.getAddTimestampMils());
        newPO.setUserMobile(oldPO.getUserMobile());
        newPO.setOpenId(oldPO.getOpenId());
        newPO.setId(oldPO.getId());
        wxUserInfoMapper.update(newPO);
    }

}
