package com.pcl.healthism.bussiness.virus.common;

import com.pcl.healthism.bussiness.common.Pair;
import com.pcl.healthism.bussiness.common.tools.StringTool;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GpsConvertApi {

    private static final String URL = "https://restapi.amap.com";
    private static final String API = "/v3/assistant/coordinate/convert?key=b68ba7e1957cb8cc9e388f5db4a5bb64&s=rsv3&locations=%s&coordsys=%s&callback=jsonp_199871_&platform=JS&logversion=2.0&appname=http://localhost:8080/kk&csid=53450B85-F73C-4BF5-BD0E-DF33605EEC4F&sdkversion=1.4.15";
    private final WebClient webClient;

    public GpsConvertApi(WebClient.Builder builder) {
        this.webClient = builder.baseUrl(URL)
                .build();
    }

    public List<Pair<Double, Double>> convertTo(List<Pair<Double, Double>> originGps, String originCoord) {
        String gps = originGps.stream().map(l -> l.getLeft().toString() + "," + l.getRight().toString())
                .collect(Collectors.joining(";"));
        Mono<String> resultMono = webClient.get().uri(String.format(API, gps, originCoord))
                .retrieve()
                .bodyToMono(String.class);
        final String PREFIX = "locations\":\"";
        String result = resultMono.block();
        if (result == null) {
            return Collections.emptyList();
        }
        int idx = result.indexOf(PREFIX);
        if (idx < 0) {
            return Collections.emptyList();
        }
        result = result.substring(idx + PREFIX.length());
        idx = result.indexOf('\"');
        String locations = result.substring(0, idx);
        return Stream.of(locations.split(";")).filter(l -> ! StringTool.isEmpty(l))
                .map(l -> StringTool.splitToD(l, ","))
                .map(l -> new Pair<>(l.get(0), l.get(1)))
                .collect(Collectors.toList());
    }
}
