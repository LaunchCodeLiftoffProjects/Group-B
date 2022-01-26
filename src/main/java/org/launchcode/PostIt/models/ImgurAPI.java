package org.launchcode.PostIt.models;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.launchcode.PostIt.models.dto.ImgurResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class ImgurAPI {
    HttpClient client = HttpClient.newHttpClient();
    public static final String CLIENTID = System.getenv("CLIENTID");

    public static String uploadImage(MultipartFile image) {

        String encodedImage = "";
        String imgUrl = "";
        try {
            encodedImage = multiEncodeFileToBase64Binary(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image", encodedImage)
                .build();
        Request request = new Request.Builder()
                .url("https://api.imgur.com/3/image")
                .method("POST", body)
                .addHeader("Authorization", "Client-ID " + CLIENTID)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            ObjectMapper mapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            ImgurResponse imgResponse = mapper.readValue(responseBody, ImgurResponse.class);
            imgUrl = imgResponse.getData().getLink();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgUrl;

    }

    private static String multiEncodeFileToBase64Binary(MultipartFile file) throws Exception{
        FileInputStream fileInputStreamReader = (FileInputStream) file.getInputStream();
        byte[] bytes = new byte[(int)file.getSize()];
        fileInputStreamReader.read(bytes);
        return new String(Base64.encodeBase64(bytes), "UTF-8");
    }

}