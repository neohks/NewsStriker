package com.nksexample.newsstrike.model;

public class FavModel {

    private  int favID;
    private String publisherName;
    private String url;

    public FavModel() {}

    public  FavModel(int favID, String publishName, String url){

        this.favID = favID;
        this.publisherName = publishName;
        this.url = url;

    }

    public int getFavID() {
        return favID;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public String getUrl() {
        return url;
    }
}
