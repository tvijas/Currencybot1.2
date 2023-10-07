package com.example.currencybot.NBPCurrencyExchangeRates;

import org.json.JSONException;
import org.springframework.stereotype.Component;

import java.io.IOException;

public class CurrencyCreator implements Currency{
    @Override
    public Double getDollar(String url) {
        try {
            return CurrencyRateGet.NpbCurrencyExchangeRate(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Double getEuro(String url) {
        try {
            return CurrencyRateGet.NpbCurrencyExchangeRate(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Double getZloty(String url) {
        try {
            return CurrencyRateGet.NpbCurrencyExchangeRate(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
