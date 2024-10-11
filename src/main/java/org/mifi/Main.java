package org.mifi;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Secret secret = null;
        Gson gson = new Gson();
        String secretFileName = "secret.json";
        try (FileReader reader = new FileReader(Main.class.getClassLoader().getResource(secretFileName).getFile())) {
            secret = gson.fromJson(reader, Secret.class);
        } catch (NullPointerException e) {
            System.out.println(secretFileName + " не найден\nСойздайте файл resources/" + secretFileName + " c наполнением:\n{\n" +
                    "  \"secret\": \"secret_key\"\n" +
                    "}");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //https://api.weather.yandex.ru/v2/forecast?lat=52.37125&lon=4.89388' -H 'X-Yandex-Weather-Key: your_key
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите Широту, Latitude, lat - идет с севера (North, N) на юг (South, S):");
        double lat = scanner.nextDouble();
        System.out.print("Введите Долготу, Longitude, lng - идет с запада (West, W) на восток (East, E):");
        double lon = scanner.nextDouble();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.weather.yandex.ru/v2/forecast?lat=" + lat + "&lon=" + lon))
                .setHeader("X-Yandex-Weather-Key", secret.getSecret())
                .GET()
                .build();
        try {
            HttpResponse<String> responseBody = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response Body: " + responseBody.body());    //Получены все данные от сервиса
            Response response = gson.fromJson(responseBody.body(), Response.class);
            int temperature = response.getFact().getTemp();
            System.out.println("Температура fact{temp}: " + temperature);   //Отдельно вывод температуры

        } catch (Exception e) {
            System.err.println("Error making HTTP request: " + e.getMessage());
        }


    }
}