package com.example.currencybot.NBPCurrencyExchangeRates;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyRateGet {
    public static double NpbCurrencyExchangeRate (String apiUrl)throws IOException, JSONException {
        double midValue = 0;
        // Создаем объект URL и открываем соединение с API
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Устанавливаем метод HTTP-запроса на GET
        connection.setRequestMethod("GET");

        // Получаем ответ от сервера
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Если запрос успешен, читаем данные из ответа
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Разбираем JSON-ответ
            String jsonResponse = response.toString();
            JSONObject jsonObject = new JSONObject(jsonResponse);

            // Получение массива из поля "rates"
            JSONArray ratesArray = jsonObject.getJSONArray("rates");

            // Получение объекта из массива
            JSONObject rateObject = ratesArray.getJSONObject(0);

            // Получение значения поля "mid"
            midValue = rateObject.getDouble("mid");


        }
        return  midValue;
    }

}
