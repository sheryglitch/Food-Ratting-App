package com.example.projectmcclass;

public class uploadinfo {
    public String imageName,imageURL,service = "0.0",quality = "0.0",hygiene = "0.0",taste = "0.0",exp ="0.0";
    public uploadinfo(){}

    public uploadinfo(String name, String url) {
        this.imageName = name;
        this.imageURL = url;
    }

    public String getRestName() {
        return imageName;
    }
    public String getImageURL() {
        return imageURL;
    }

    public String getService() {
        return service;
    }

    public String getQuality() {
        return quality;
    }

    public String getHygiene() {
        return hygiene;
    }

    public String getTaste() {
        return taste;
    }

    public String getExp() {
        return exp;
    }

    public void setRestName(String imageName) {
        this.imageName = imageName;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setService(String service) {
        this.service = service;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public void setHygiene(String hygiene) {
        this.hygiene = hygiene;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }
}
