package org.launchcode.PostIt.models.dto;

public class imgurDataResponse {

    private String link;

    private String deletehash;

    public imgurDataResponse(){};

    public imgurDataResponse(String link, String deletehash) {
        this.link = link;
        this.deletehash = deletehash;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDeletehash() {
        return deletehash;
    }

    public void setDeletehash(String deletehash) {
        this.deletehash = deletehash;
    }
}
