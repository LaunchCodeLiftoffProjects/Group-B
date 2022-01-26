package org.launchcode.PostIt.models.dto;

public class ImgurResponse {
    private imgurDataResponse data;

    public ImgurResponse(){};
    public ImgurResponse(imgurDataResponse data) {
        this.data = data;
    }
    public imgurDataResponse getData() {
        return data;
    }

    public void setData(imgurDataResponse data) {
        this.data = data;
    }



}
