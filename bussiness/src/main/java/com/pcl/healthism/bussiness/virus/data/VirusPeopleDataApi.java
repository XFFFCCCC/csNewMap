package com.pcl.healthism.bussiness.virus.data;

import com.pcl.healthism.bussiness.virus.data.VirusData;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class VirusPeopleDataApi {

    private final WebClient webClient;

    private static final String URL  = "http://tianqiapi.com";
    /**
     * springboot 会自动初始化一个prototype的client，
     * 引入spring-boot-starter-webflux后
     * */
    public VirusPeopleDataApi(WebClient.Builder builder) {

        this.webClient = builder.baseUrl(URL)
                .build();
        //如果要请求的这个网站包含特定的头部信息或固定的参数可以个性化添加,提升性能
//        this.webClient = builder.baseUrl("http://www.baidu.com")
//                .defaultCookie("auth", "xxxxx,sdjlsalkjhlkjhdd")
//                .defaultCookie("xxcokie", "xxxsd1212")
//                .defaultHeader("xxds", "sdsdsdsdsdsd")
//                .build();
    }

    public VirusData get() {
        //这一步其实是异步调用立即返回,这种用法的好处是，当我们同时调用多个http接口时，可以并发调用，提升性能
        Mono<DTO> result = webClient.get().uri("/api?version=epidemic&appid=76647432&appsecret=kYK4ZquK")
                .retrieve()
                .bodyToMono(DTO.class);
        //等待返回结果
        return result.block().getData();
    }


    @Data
    private static class DTO {
        private int code;
        private VirusData data;
    }

}
