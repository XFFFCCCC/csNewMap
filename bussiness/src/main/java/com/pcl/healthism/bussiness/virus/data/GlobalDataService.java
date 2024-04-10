package com.pcl.healthism.bussiness.virus.data;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class GlobalDataService {

    private static final String VIRUS_KEY = "VIRUS_KEY";
    private LoadingCache<String, VirusData> virusDataLoadingCache;
    private static ListeningExecutorService executorService = MoreExecutors
            .listeningDecorator(Executors.newFixedThreadPool(1));
    private final VirusPeopleDataApi virusPeopleDataApi;

    @PostConstruct
    private void init() {
        virusDataLoadingCache = CacheBuilder.newBuilder()
                .maximumSize(10)
                .refreshAfterWrite(5, TimeUnit.MINUTES)
                .build(new CacheLoader<String, VirusData>() {
                        @Override
                        public VirusData load(String key) throws Exception {
                            return innerGetData();
                        }

                        @Override
                        public ListenableFuture<VirusData> reload(String key, VirusData oldValue) throws Exception {
                            return executorService.submit(() -> {
                                VirusData data = innerGetData();
                                if (data == null) {
                                    return oldValue;
                                }
                                return data;
                            });
                        }
                    }
                );
    }

    public VirusData get() {
        try {
            return virusDataLoadingCache.get(VIRUS_KEY);
        } catch (ExecutionException e) {
            return null;
        }
    }

    private VirusData innerGetData() {
        return virusPeopleDataApi.get();
    }
}
