package com.pcl.healthism.bussiness.virus.poi;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class PoiService {

    private static final String POI_KEY = "POI_KEY";
    private LoadingCache<String, Poi> poiLoadingCache;
    private static ListeningExecutorService executorService = MoreExecutors
            .listeningDecorator(Executors.newFixedThreadPool(1));

    private final Parser parser;

    @PostConstruct
    private void init() {
        poiLoadingCache = CacheBuilder.newBuilder()
                .maximumSize(10)
                .refreshAfterWrite(3, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Poi>() {
                           @Override
                           public Poi load(String key) throws Exception {
                               return innerGetData();
                           }

                           @Override
                           public ListenableFuture<Poi> reload(String key, Poi oldValue) throws Exception {
                               return executorService.submit(() -> {
                                   Poi data = innerGetData();
                                   if (data == null) {
                                       return oldValue;
                                   }
                                   return data;
                               });
                           }
                       }
                );
    }


    public Poi get() {
        try {
            return poiLoadingCache.get(POI_KEY);
        } catch (ExecutionException e) {
            log.error("load config error", e);
            return null;
        }
    }


    private Poi innerGetData() {
        Poi poi  = new Poi();
        try {
            poi.setCommunities(parser.loadCommunity());
            poi.setHospitals(parser.loadHospitals());
        } catch (Exception e) {
            log.error("load config error", e);
            return null;
        }
        return poi;
    }
}
