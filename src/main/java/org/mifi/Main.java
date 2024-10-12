package org.mifi;

import com.google.gson.Gson;
import org.mifi.entity.Forecasts;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введете широту: ");
        double lat = scanner.nextDouble();
        System.out.print("Введете долготу: ");
        double lon = scanner.nextDouble();
        System.out.print("Введете колличество дней: ");
        int limit = scanner.nextInt();  //Для тарифа «Тестовый» максимально допустимое значение — 7.
        int summ = 0;
        Gson gson = new Gson();
        Weather weather = new Weather();
        String responseBody = weather.getResponseBodyFromLatAndLonByLimit(lat, lon, limit);
        Response response = gson.fromJson(responseBody, Response.class);
        System.out.println("Температура сегодня: " + response.getFact().getTemp() + "°C");
        for (Forecasts forecasts : response.getForecasts()) summ += forecasts.getTempAvgDay();
        String start = dateFormat(response.getForecasts().getFirst().getDate());
        String stop = dateFormat(response.getForecasts().getLast().getDate());
        System.out.println("Средняя температура c " + start + " по " + stop + " равна: " + (limit == 0 ? summ : summ / limit) + "°C");
    }

    private static String dateFormat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy");
        return simpleDateFormat.format(date);
    }
}