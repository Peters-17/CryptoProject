package com.atguigu.crypto.controller;

import com.atguigu.crypto.aspect.ExecTimeLog;
import com.atguigu.crypto.model.Kline;
import com.atguigu.crypto.service.CryptoService;
import com.atguigu.crypto.service.InputValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CryptoController {

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private InputValidationService validationService;

    @ExecTimeLog
    @PostMapping("/load")
    public ResponseEntity<?> loadData(@RequestParam String symbol,
                                      @RequestParam Long startTime,
                                      @RequestParam Long endTime) {
        //validationService.validateSymbol(symbol);
        validationService.validateTimeRange(startTime, endTime);

        cryptoService.loadData(symbol, startTime, endTime);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/aggregated-data")
    public ResponseEntity<?> getAggregatedData(
            @RequestParam String symbol,
            @RequestParam Long startTime,
            @RequestParam Long endTime) {

        List<Kline> data = cryptoService.loadAggregatedData(symbol, startTime, endTime);
        return ResponseEntity.ok(data);
    }
}
