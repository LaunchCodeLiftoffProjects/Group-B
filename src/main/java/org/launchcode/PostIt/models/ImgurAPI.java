package org.launchcode.PostIt.models;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.http.HttpClient;

public class ImgurAPI {

    HttpClient client = HttpClient.newHttpClient();

    public static String uploadImage(@RequestParam("image") MultipartFile image) {

        return "index";

//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://api.imgur.com/3/upload"))
//                .timeout(Duration.ofMinutes(1))
//                .header("Authorization", "Client-ID {{clientId}}")
//                .POST(HttpRequest.BodyPublishers.ofInputStream(() -> {
//                    try {
//                        return new ByteArrayInputStream(image.getInputStream());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                ))
//                .build();
//        // Async response handler
//        CompletableFuture<HttpResponse<String>> response = HttpClient.newBuilder()
//                .build()
//                .sendAsync(request, HttpResponse.BodyHandlers.ofString());

    }

    private static String encodeFileToBase64Binary(File file) throws Exception{
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        fileInputStreamReader.read(bytes);
        return new String(Base64.encodeBase64(bytes), "UTF-8");
    }
}