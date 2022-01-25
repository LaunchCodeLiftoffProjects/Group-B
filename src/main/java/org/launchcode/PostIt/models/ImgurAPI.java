package org.launchcode.PostIt.models;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class ImgurAPI {

    HttpClient client = HttpClient.newHttpClient();

    public static getImage(String clientId) throws Exception {


    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.imgur.com/3/image{{imageHash}}"))
            .timeout(Duration.ofMinutes(1))
            .header("Authorization", "Client-ID {{clientId}}")
            .GET()
            .build();
    // Async response handler
        CompletableFuture<HttpResponse<String>> response = HttpClient.newBuilder()
                .build()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());

        return /* Something */;
    }


    public static uploadImage(/*Something*/) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.imgur.com/3/upload"))
                .timeout(Duration.ofMinutes(1))
                .header(/*header info*/)
                .POST()
                .build();
        // Async response handler
        CompletableFuture<HttpResponse<String>> response = HttpClient.newBuilder()
                .build()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());

    }

    private static String encodeFileToBase64Binary(File file) throws Exception{
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        fileInputStreamReader.read(bytes);
        return new String(Base64.encodeBase64(bytes), "UTF-8");
    }
}