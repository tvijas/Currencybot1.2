package com.example.currencybot.NBPCurrencyExchangeRates;

public interface Currency {
    Double getDollar(String url);
    Double getEuro(String url);
    Double getZloty(String url);
}
