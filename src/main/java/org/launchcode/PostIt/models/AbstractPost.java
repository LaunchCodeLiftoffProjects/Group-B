package org.launchcode.PostIt.models;


import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.util.Date;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractPost {
    @Id
    @GeneratedValue
    private int id;

    //Add validation after merging branch that imports validation
    private String title;

    private Date date;

    private Boolean anonymous;

    @ManyToOne
    User user;

    public AbstractPost (){
    }
    public AbstractPost(String title, Boolean anon){
        this.title = title;
        this.date = new Date();
        this.anonymous = anon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Boolean getAnonymous() {
        return anonymous;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractPost that = (AbstractPost) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
