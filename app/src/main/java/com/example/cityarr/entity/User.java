package com.example.cityarr.entity;

public class User {


    private String email;
    private String fullName;
    private String phone;
    private String password;
    private String gov;
    private String min;

    public User() {}

    public User(String fullName, String email, String phone) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    public User(String fullName, String email,String phone,String gov, String min) {
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.gov = gov;
        this.min = min;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getGov() {
        return gov;
    }

    public String getMin() {
        return min;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGov(String gov) {
        this.gov = gov;
    }

    public void setMin(String min) {
        this.min = min;
    }
}
