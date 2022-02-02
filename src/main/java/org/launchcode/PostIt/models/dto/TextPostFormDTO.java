package org.launchcode.PostIt.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TextPostFormDTO extends PostFormDTO{

    @NotNull
    @Size(max = 2000, message = "Maximum of 2,000 characters.")
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String aBody) {
        body = aBody;
    }
}
