package com.pcl.healthism.bussiness.common.gateway;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/***
 * 项目统一选用Spring5 的WebClient作为http client，该client采用reactor的异步API设计
 * 并且被测试具有极高的性能，并被业界广泛使用
 * 关于httpclient的更为细致的用法参考：https://elim.iteye.com/blog/2427658
 */
@Component
public class HttpExample {
    /***
     * 该webclient初始化之后是线程安全的，
     */
    private final WebClient webClient;
    /**
     * springboot 会自动初始化一个prototype的client，
     * 引入spring-boot-starter-webflux后
     * */
    public HttpExample(WebClient.Builder builder) {

        this.webClient = builder.baseUrl("http://www.baidu.com")
                .build();
        //如果要请求的这个网站包含特定的头部信息或固定的参数可以个性化添加,提升性能
//        this.webClient = builder.baseUrl("http://www.baidu.com")
//                .defaultCookie("auth", "xxxxx,sdjlsalkjhlkjhdd")
//                .defaultCookie("xxcokie", "xxxsd1212")
//                .defaultHeader("xxds", "sdsdsdsdsdsd")
//                .build();
    }

    public String baiduHomePage() {
        //这一步其实是异步调用立即返回,这种用法的好处是，当我们同时调用多个http接口时，可以并发调用，提升性能
        Mono<String> result = webClient.get().uri("/")
                .retrieve()
                .bodyToMono(String.class);
        //等待返回结果
        return result.block();
    }

}
