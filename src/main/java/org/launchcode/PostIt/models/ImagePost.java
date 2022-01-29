package org.launchcode.PostIt.models;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class ImagePost extends AbstractPost{

    @NotNull
    private String url;

    public ImagePost(){};

    public ImagePost(String title, String aUrl, Boolean anon){
        super(title, anon, "image");
        this.url = aUrl;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
