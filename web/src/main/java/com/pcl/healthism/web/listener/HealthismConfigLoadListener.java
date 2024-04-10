package com.pcl.healthism.web.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 自定义SpringApplicationRunListener （应用配置加载器，方便线上分环境配置示例）
 * 我们可以通过该扩展来改变很多springboot的初始化行为，比如我们可以通过这个扩展加载远程配置
 * 做相应的在springboot初始化期间，需要同时进行的其他事情
 * springboot支持的这种可拆卸模块的方式，可以在resources/META_INF下的spring.factorues文件添加如下配置，即可启用
 * org.springframework.boot.SpringApplicationRunListener=\
 *   com.pcl.healthism.web.listener.HealthismConfigLoadListener
 * 该类会在springboot初始化工厂的时候率先实例化，而此时的log4j2还没启用，
 * 所以该处使用log4j2的方式打印日志不会生效
 * WARN: 这里有个非紧急的问题待解决，由于是初期加载的类，此时的log4j2还没初始化完成，打印的日志会被丢弃
 */
public class HealthismConfigLoadListener implements SpringApplicationRunListener, PriorityOrdered {
    private SpringApplication app;
    private String[] runArgs;
    private final static Log LOG = LogFactory.getLog(HealthismConfigLoadListener.class);
    private final static String FILE_PATTERN = "classpath:application*.yml"; //自定义springboot启动配置文件的pattern
    private AtomicInteger propertieFileNum;

    public HealthismConfigLoadListener(SpringApplication application, String args[]) {
        this.app = application;
        this.runArgs = args;
        this.propertieFileNum = new AtomicInteger(0);
    }

    @Override
    public void starting() {
        LOG.info(app.toString());
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        LOG.info("prepare to load self definition application properties");
        List<PropertySource> propertySources = loadSelfDefProperties();
        if (!propertySources.isEmpty()) {
            LOG.info("load self definition properties file counts:" + propertySources.size());
            MutablePropertySources sources = environment.getPropertySources();
            for (PropertySource propertySource : propertySources) {
                sources.addLast(propertySource);
            }
        }
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {

    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {

    }

    @Override
    public void started(ConfigurableApplicationContext context) {

    }

    @Override
    public void running(ConfigurableApplicationContext context) {

    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {

    }

    @Override
    public int getOrder() {
        return 0;
    }

    private List<PropertySource> loadSelfDefProperties(){
        try {
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(FILE_PATTERN);
            if (resources.length == 0) {
                return Collections.EMPTY_LIST;
            }
            return Stream.of(resources).map(this::fromResource).flatMap(l -> l.stream()).collect(Collectors.toList());
        } catch (IOException e) {
            LOG.error("load self definition properties error", e);
            throw new IllegalStateException(e);
        }
    }

    private List<PropertySource<?>> fromResource(Resource resource) {
        try {
            YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
            int index = propertieFileNum.addAndGet(1);
            return loader.load("self-def-properties" + index, resource);
        } catch (IOException e) {
            LOG.error("load self definition properties error", e);
            throw new IllegalStateException(e);
        }
    }
}
