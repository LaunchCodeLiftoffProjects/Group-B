package org.launchcode.PostIt.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public abstract class PostFormDTO {

    @NotNull
    @NotBlank
    @Size(min=3,max=120,message = "Title must be between 3 and 120 characters.")
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
