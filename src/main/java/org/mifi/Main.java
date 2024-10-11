package org.mifi;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String secret = "";
        //https://api.weather.yandex.ru/v2/forecast?lat=52.37125&lon=4.89388' -H 'X-Yandex-Weather-Key: your_key
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите Широту, Latitude, lat - идет с севера (North, N) на юг (South, S):");
        double lat = scanner.nextDouble();
        System.out.print("Введите Долготу, Longitude, lng - идет с запада (West, W) на восток (East, E):");
        double lon = scanner.nextDouble();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.weather.yandex.ru/v2/forecast?lat=" + lat + "&lon=" + lon))
                .setHeader("X-Yandex-Weather-Key", secret)
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
        } catch (Exception e) {
            System.err.println("Error making HTTP request: " + e.getMessage());
        }


    }
}