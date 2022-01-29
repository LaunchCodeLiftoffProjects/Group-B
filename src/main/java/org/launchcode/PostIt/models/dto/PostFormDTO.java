package org.launchcode.PostIt.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public abstract class PostFormDTO {


    private String title;

    private Boolean anonymous;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }
}
