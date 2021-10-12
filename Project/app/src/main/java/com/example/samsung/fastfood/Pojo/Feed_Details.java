package com.example.samsung.fastfood.Pojo;

/**
 * Created by SAMSUNG on 12/1/2017.
 */

public class Feed_Details {
    private String user;
    private String comment;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Feed_Details(String user, String comment, String email) {
        this.user = user;
        this.comment = comment;
        this.email=email;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
