package org.mifi;

import com.google.gson.Gson;
import org.mifi.entyti.Forecasts;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) {
        final double LAT = 66.37125;
        final double LON = 66.23388;
        final int LIMIT = 7;  //Для тарифа «Тестовый» максимально допустимое значение — 7.
        int summ = 0;
        Gson gson = new Gson();
        Weather weather = new Weather();
        String responseBody = weather.getResponseBodyFromLatAndLonByLimit(LAT, LON, LIMIT);
        System.out.println("Response Body: " + responseBody);
        Response response = gson.fromJson(responseBody, Response.class);
        System.out.println("Температура fact{temp}: " + response.getFact().getTemp() + "°C");
        for (Forecasts forecasts : response.getForecasts()) summ += forecasts.getTempAvgDay();
        String start = dateFormat(response.getForecasts().getFirst().getDate());
        String stop = dateFormat(response.getForecasts().getLast().getDate());
        System.out.println("Средняя температура c " + start + " по " + stop + " равна: " + (LIMIT == 0 ? summ : summ / LIMIT) + "°C");
    }

    private static String dateFormat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy");
        return simpleDateFormat.format(date);
    }
}