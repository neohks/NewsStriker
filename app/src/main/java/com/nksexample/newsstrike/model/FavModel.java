package com.nksexample.newsstrike.model;

public class FavModel {

    private  int favID;
    private String publisherName;
    private String articleName;
    private String url;

    public FavModel() {}

    public  FavModel(int favID, String publishName, String articleName, String url){

        this.favID = favID;
        this.publisherName = publishName;
        this.articleName = articleName;
        this.url = url;

    }

    public int getFavID() {
        return favID;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public String getArticleName() {
        return articleName;
    }

    public String getUrl() {
        return url;
    }
}
