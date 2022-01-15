package org.launchcode.PostIt.models;

public class Post extends AbstractPost{
    //add validation
    private String body;

    public Post(){
    }

    public Post(String title, String body){
        super(title);
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
