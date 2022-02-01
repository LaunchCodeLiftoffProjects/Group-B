package org.launchcode.PostIt.models.dto;

import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class ImagePostDTO extends PostFormDTO{

    private String url;

    private MultipartFile image;

    public MultipartFile getImage() {return image;}

    public void setImage(MultipartFile image) {this.image = image;}

    public String getUrl() {return url;}

    public void setUrl(String newUrl) {
        url = newUrl;
    }
}
