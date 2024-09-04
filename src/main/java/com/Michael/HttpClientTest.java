package com.Michael;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpClientTest {
    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("Response: " + response.body().string());
            } else {
                System.out.println("Request failed: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}