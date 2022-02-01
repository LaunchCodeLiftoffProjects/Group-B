package org.launchcode.PostIt.models.dto;

import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;

public class ImagePostDTO extends PostFormDTO{

    @NotBlank
    @NotNull
    @URL
    private String url;

    @NotNull(message = "You must upload an image to post.")
    private MultipartFile image;

    public MultipartFile getImage() {return image;}

    public void setImage(MultipartFile image) {this.image = image;}

    public String getUrl() {return url;}

    public void setUrl(String newUrl) {
        url = newUrl;
    }
}
