//package com.atguigu.crypto.service;
//
//import com.atguigu.crypto.model.Kline;
//import com.atguigu.crypto.repository.KlineRepository;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//
//import org.springframework.stereotype.Service;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.client.RestTemplate;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Validated
//@Service
//public class CryptoService {
//
//    @Autowired
//    private KlineRepository klineRepository;
//
//    @Autowired
//    private BinanceApiService apiService;
//
//    public void loadData(@NotBlank String symbol, @NotNull Long startTime, @NotNull Long endTime) {
//        //TODO for loop or stream to exceed the data limit
//        List<Kline> klines = apiService.getKlines(symbol, startTime, endTime);
//        klineRepository.insertBatch(klines);
//    }
//
//}
package com.atguigu.crypto.service;

import com.atguigu.crypto.model.Kline;
import com.atguigu.crypto.repository.KlineRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Validated
@Service
public class CryptoService {

    @Autowired
    private KlineRepository klineRepository;

    @Autowired
    private BinanceApiService apiService;

    @Value("${binance.api.chunkSize:3600000}") // 1小时 = 3600000毫秒
    private long chunkSize; // 单位：毫秒

    public void loadData(@NotBlank String symbol, @NotNull Long startTime, @NotNull Long endTime) {
        Long currentTime = startTime;



        long period = 500 * 60 * 60 * 1000; //小时为单位


        LongStream.range(startTime, endTime)
                    .parallel()
                    .filter(i -> (i-startTime)%period ==0)
                    .forEach(i->{
                        long currEnd = i + period > endTime? endTime: i + period;
                        List<Kline> klines = apiService.getKlines(symbol,i,currEnd);
                        klineRepository.insertBatch(klines);
                    });

    }

    @Cacheable(value = "aggregatedData", key = "#symbol.concat(#startTime.toString()).concat(#endTime.toString())")
    public List<Kline> loadAggregatedData(String symbol, Long startTime, Long endTime) {
        return klineRepository.findDataNewInterval(symbol, startTime, endTime);
    }
}

// 用lettuce写个redis