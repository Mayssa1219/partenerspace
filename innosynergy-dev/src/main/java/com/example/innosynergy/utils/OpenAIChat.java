package com.example.innosynergy.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*; // Importez uniquement les classes nécessaires d'OkHttp

import java.io.IOException;

public class OpenAIChat {

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "sk-proj--6_rjfUnOxcGnT0vUjOHZnUkmWQv17uf1jpLGWL4ibHP" +
            "QjQWMD7oSyy0FjXlQURmswEgUFzf9ZT3BlbkFJGCfTJxLe4arA6-K-8MpxLKkzVQ6sXNXxhlPSLUNbLSbl" +
            "gV85LWWc9NZJooB_ST49clg02Df8sA"; // Remplacez par votre clé API

    private final OkHttpClient httpClient = new OkHttpClient();

    public String sendMessage(String message) throws IOException, InterruptedException {
        int maxRetries = 3; // Nombre maximal de tentatives
        int retryDelay = 1000; // Délai initial en millisecondes

        for (int attempt = 0; attempt < maxRetries; attempt++) {
            String jsonBody = "{"
                    + "\"model\": \"gpt-3.5-turbo\","
                    + "\"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}]"
                    + "}";

            RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(API_URL)
                    .post(body)
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .build();

            try (okhttp3.Response response = httpClient.newCall(request).execute()) {
                if (response.code() == 429) {
                    // Si erreur 429, attendre avant de réessayer
                    Thread.sleep(retryDelay);
                    retryDelay *= 2; // Délai exponentiel
                    continue;
                }

                if (!response.isSuccessful()) {
                    throw new IOException("Erreur lors de la requête : " + response.code() + " - " + response.message());
                }

                // Parser la réponse JSON
                String responseBody = response.body().string();
                JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
                return jsonResponse.getAsJsonArray("choices")
                        .get(0).getAsJsonObject()
                        .getAsJsonObject("message")
                        .get("content").getAsString();
            }
        }

        throw new IOException("Échec après " + maxRetries + " tentatives.");
    }
}