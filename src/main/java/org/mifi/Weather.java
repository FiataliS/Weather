package org.mifi;

import com.google.gson.Gson;
import org.mifi.entity.Secret;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Weather {
    public String getResponseBodyFromLatAndLonByLimit(double lat, double lon, int limit) {
        if (limit == 0) limit = 1;
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.weather.yandex.ru/v2/forecast?lat=" + lat + "&lon=" + lon + "&limit=" + limit))
                .setHeader("X-Yandex-Weather-Key", secret())
                .GET()
                .build();
        try {
            HttpResponse<String> responseBody = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return responseBody.body();
        } catch (Exception e) {
            System.err.println("Error making HTTP request: " + e.getMessage());
        }
        return "";
    }

    private String secret() {
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
        return secret.getSecret();
    }
}
