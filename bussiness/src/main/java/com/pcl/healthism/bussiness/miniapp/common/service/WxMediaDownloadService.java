package com.pcl.healthism.bussiness.miniapp.common.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.pcl.healthism.bussiness.common.tools.FileTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
@RequiredArgsConstructor
public class WxMediaDownloadService {
    private  WxMaService wxMaService;

    public File download(String mediaId) {
        try {
            return wxMaService.getMediaService().getMedia(mediaId);
        } catch (WxErrorException e) {
            log.error("download media error,{}, mediaId={}", e.getError(), mediaId);
        }
        return null;
    }

    public boolean download(String media, String targetFilePath) {
        File file = download(media);
        if (file == null) {
            return false;
        }
        FileTool.copyFile(file.getPath(), targetFilePath);
        return true;
    }
}
