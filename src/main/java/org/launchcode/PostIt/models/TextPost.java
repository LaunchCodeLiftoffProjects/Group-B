package org.launchcode.PostIt.models;

import javax.persistence.Entity;

@Entity
public class TextPost extends AbstractPost{
    //add validation
    private String body;

    public TextPost(){
    }

    public TextPost(String title, String body, Boolean anon){
        super(title, anon, "text");
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
