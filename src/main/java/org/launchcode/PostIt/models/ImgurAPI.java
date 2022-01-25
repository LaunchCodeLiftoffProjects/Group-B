package org.launchcode.PostIt.models;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class ImgurAPI {
    HttpClient client = HttpClient.newHttpClient();

    public static void uploadImage(MultipartFile image) {

        byte[] imageByte = new byte[0];
        try {
            imageByte = image.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.imgur.com/3/upload"))
                .timeout(Duration.ofMinutes(1))
                .header("Authorization", "Client-ID d9e603a5e9dfbff")
                .header("Content-Type","image/jpeg")
                .POST(HttpRequest.BodyPublishers.ofByteArray(imageByte))
                .build();
        // response handler
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String code = response.toString();
        //return
    }

    private static String encodeFileToBase64Binary(File file) throws Exception{
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        fileInputStreamReader.read(bytes);
        return new String(Base64.encodeBase64(bytes), "UTF-8");
    }

}