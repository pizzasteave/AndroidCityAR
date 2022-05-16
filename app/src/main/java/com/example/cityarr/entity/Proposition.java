package com.example.cityarr.entity;

public class Proposition {

    private String id;
    private String title ;
    private String imageURL;



    public Proposition(){

    }


    public Proposition(String id , String title , String imageURL) {
        this.id = id ;
        this.title = title ;
        this.imageURL = imageURL;
    }


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String gettitle() {
        return title;
    }

    public void settitlee(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
