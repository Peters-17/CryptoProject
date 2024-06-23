//package com.atguigu.crypto.service;
//
//import com.atguigu.crypto.model.Kline;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.math.BigDecimal;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Objects;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Service
//public class BinanceApiService {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Value("${urlTemplate}")
//    private String urlTemplate;
//
//    public List<Kline> getKlines(String symbol, Long startTime, Long endTime){
//        String url = String.format(urlTemplate, symbol, startTime, endTime);
//        ResponseEntity<String[][]> response = restTemplate.getForEntity(url, String[][].class);
//
//        return Arrays.stream(Objects.requireNonNull(response.getBody()))
//                .parallel()
//                .map(klineData -> createKlineFromData(klineData, symbol))
//                .collect(Collectors.toList());
//    }
//
//    public Set<String> getSymbols(){
//        return Set.of();
//    }
//
//    private Kline createKlineFromData(String[] klineData, String symbol) {
//        Kline kline = new Kline();
//        kline.setOpenTime(Long.parseLong(klineData[0]));
//        kline.setCloseTime(Long.parseLong(klineData[6]));
//        kline.setOpenPrice(new BigDecimal(klineData[1]));
//        kline.setHighPrice(new BigDecimal(klineData[2]));
//        kline.setLowPrice(new BigDecimal(klineData[3]));
//        kline.setClosePrice(new BigDecimal(klineData[4]));
//        kline.setVolume(new BigDecimal(klineData[5]));
//        kline.setQuoteAssetVolume(new BigDecimal(klineData[7]));
//        kline.setNumberOfTrades(Integer.parseInt(klineData[8]));
//        kline.setTakerBuyBaseAssetVolume(new BigDecimal(klineData[9]));
//        kline.setTakerBuyQuoteAssetVolume(new BigDecimal(klineData[10]));
//        kline.setSymbolKline(symbol);
//        return kline;
//    }
//}
package com.atguigu.crypto.service;

import com.atguigu.crypto.model.Kline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BinanceApiService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${urlTemplate}")
    private String urlTemplate;

    public List<Kline> getKlines(String symbol, Long startTime, Long endTime) {
        String url = String.format(urlTemplate, symbol, startTime, endTime);
        ResponseEntity<String[][]> response = restTemplate.getForEntity(url, String[][].class);

        return Arrays.stream(Objects.requireNonNull(response.getBody()))
                .parallel()
                .map(klineData -> createKlineFromData(klineData, symbol))
                .collect(Collectors.toList());
    }

    public Set<String> getSymbols() {
        return Set.of();
    }

    private Kline createKlineFromData(String[] klineData, String symbol) {
        Kline kline = new Kline();
        kline.setOpenTime(Long.parseLong(klineData[0]));
        kline.setCloseTime(Long.parseLong(klineData[6]));
        kline.setOpenPrice(new BigDecimal(klineData[1]));
        kline.setHighPrice(new BigDecimal(klineData[2]));
        kline.setLowPrice(new BigDecimal(klineData[3]));
        kline.setClosePrice(new BigDecimal(klineData[4]));
        kline.setVolume(new BigDecimal(klineData[5]));
        kline.setQuoteAssetVolume(new BigDecimal(klineData[7]));
        kline.setNumberOfTrades(Integer.parseInt(klineData[8]));
        kline.setTakerBuyBaseAssetVolume(new BigDecimal(klineData[9]));
        kline.setTakerBuyQuoteAssetVolume(new BigDecimal(klineData[10]));
        kline.setSymbolKline(symbol);
        return kline;
    }
}
