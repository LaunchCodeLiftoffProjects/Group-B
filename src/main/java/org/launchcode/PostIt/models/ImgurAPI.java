package org.launchcode.PostIt.models;

import okhttp3.*;
import org.apache.tomcat.util.codec.binary.Base64;
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

    public static void uploadImage(MultipartFile image) {

        String encodedImage = "";
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
                .addHeader("Authorization", "Client-ID d9e603a5e9dfbff")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            //String imgUrl = responseBody.substring(responseBody.indexOf("link") + 6, responseBody.indexOf(".jpeg") + 4);
            Boolean debug = response.isSuccessful();

        } catch (IOException e) {
            e.printStackTrace();
        }
//        byte[] imageByte = new byte[0];
//        try {
//            imageByte = image.getBytes();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String postBody = null;
//        try {
//            postBody = "image: " + multiEncodeFileToBase64Binary(image);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //File imageFile = new File("src/main/resources/tempImage.tmp");
//
//
////        for(byte x : imageByte){
////            postBody+= x;
////        }
////        ArrayList<Byte> postBytes = new ArrayList<>();
////        for(byte x : postBody.getBytes()){
////            postBytes.add(x);
////        }
////        for(byte t : imageByte){
////            postBytes.add(t);
////        }
////        byte postBodyByteArr[] = new byte[postBytes.size()];
////        for(int i = 0; i < postBytes.size(); i++){
////            postBodyByteArr[i] = postBytes.get(i);
////        }
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://api.imgur.com/3/image"))
//                .timeout(Duration.ofMinutes(1))
//                .header("Authorization", "Client-ID d9e603a5e9dfbff")
//                .header("Content-Type", "multipart/form-data; charset=utf-8; boundary='another cool boundary'")
//                .POST()
//                .build();
//        // response handler
//        HttpResponse<String> response = null;
//        try {
//            response = HttpClient.newBuilder()
//                    .build()
//                    .send(request, HttpResponse.BodyHandlers.ofString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        String code = response.toString();
//        //return
    }

    private static String encodeFileToBase64Binary(File file) throws Exception{
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        fileInputStreamReader.read(bytes);
        return new String(Base64.encodeBase64(bytes), "UTF-8");
    }
    private static String multiEncodeFileToBase64Binary(MultipartFile file) throws Exception{
        FileInputStream fileInputStreamReader = (FileInputStream) file.getInputStream();
        byte[] bytes = new byte[(int)file.getSize()];
        fileInputStreamReader.read(bytes);
        return new String(Base64.encodeBase64(bytes), "UTF-8");
    }

}