package com.atguigu.crypto.service;

import com.atguigu.crypto.model.exception.InputException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class InputValidationService {

    @Autowired
    private BinanceApiService apiService;

    public void validateSymbol(String symbol){
        // binance api to verify symbol
        if(!apiService.getSymbols().contains(symbol)){
            throw new InputException("....");
        }
    }

    public void validateTimeRange(@NotNull @Min(0) Long startTime, @NotNull Long endTime){
        if(startTime >= endTime){
            throw new InputException(String.format("Input startTime = %s, endTime = %s," +
                    " startTime must smaller than endTime", startTime, endTime));
        }
    }
}
