package com.example.test.best_travel.infrastructure.helpers;

import java.util.Currency;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.test.best_travel.infrastructure.dtos.CurrencyDTO;



@Component
public class ApiCurrencyConnectorHelper {
    
    private final WebClient currencyWebClient;

    @Value(value = "${api.base-currency}")
    private String baseCurrency;

    public ApiCurrencyConnectorHelper(WebClient currencyWebClient) {
        this.currencyWebClient = currencyWebClient;
    }

    private final String BASE_CURRENCY_QUERY_PARAM = "?base={base}";
    private final String SYMBOL_CURRENCY_QUERY_PARAM = "&symbols={symbol}";
    private final String CURRENCY_PATH = "/exchangerates_data/latest";
    
   public CurrencyDTO getCurrency(Currency currency){
        return this.currencyWebClient
            .get()
            .uri( uri-> uri.path(CURRENCY_PATH)
                .query(BASE_CURRENCY_QUERY_PARAM)
                .query(SYMBOL_CURRENCY_QUERY_PARAM)
                .build(baseCurrency,currency.getCurrencyCode()))
            .retrieve()
            .bodyToMono(CurrencyDTO.class)
            .block();
   }

}
