package org.launchcode.PostIt.models;


import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractPost implements Comparable<AbstractPost>{
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 3, max = 140)
    private String title;

    @NotNull
    private Date date;

    private Boolean anonymous;

    @NotNull
    private String type;

    @ManyToOne
    User user;

    public AbstractPost (){
    }
    public AbstractPost(String title, Boolean anon, String type){
        this.title = title;
        this.date = new Date();
        this.anonymous = anon;
        this.type= type;
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

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public Boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }

    public User getUser() {
        return user;
    }

    public String getType() {
        return type;
    }

    public String getFormattedDate(){
        return this.date.toString();
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int compareTo(AbstractPost post){
        return getDate().compareTo(post.getDate()) * -1;
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


