package com.example.cityarr.entity;

public class Proposition {

    private String title ;
    private String imageURL;


    public Proposition(){

    }

    public Proposition(String title , String imageURL) {
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


}
