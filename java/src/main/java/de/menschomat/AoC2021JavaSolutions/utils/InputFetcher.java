package de.menschomat.AoC2021JavaSolutions.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class InputFetcher {

    public static String getInput(int year, int day) {
        try {
            String token = Files.readString(Path.of("sessionToken.txt"), StandardCharsets.UTF_8);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format("https://adventofcode.com/%s/day/%s/input", year, day))).setHeader("Cookie",String.format("session=%s",token))
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }
}
