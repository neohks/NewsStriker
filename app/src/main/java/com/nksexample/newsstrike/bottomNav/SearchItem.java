package com.nksexample.newsstrike.bottomNav;

public class SearchItem {

    private String topicName;
    private int imageID;

    public SearchItem(String topicName, int imageID){
        this.imageID = imageID;
        this.topicName = topicName;

    }

    public int getImageID() { return imageID; }

    public String getTopicName() {return topicName;}

}
