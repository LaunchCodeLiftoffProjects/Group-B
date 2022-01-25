package org.launchcode.PostIt.models.dto;

public class ImagePostDTO extends PostFormDTO{
    private String url;

    public String getUrl() {return url;}

    public void setUrl(String newUrl) {
        url = newUrl;
    }
}
