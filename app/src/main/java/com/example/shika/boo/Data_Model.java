package com.example.shika.boo;

/**
 * Created by Shika on 06/03/2018.
 */

public class Data_Model {

    // Getter and Setter model for recycler view items
    private String title;
    private int points;
    private String duration;
    private String image;

    public Data_Model(String title,int points,String duration, String image) {

        this.title = title;
        this.points = points;
        this.duration = duration;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public int getBanner() {
        return points;
    }
    public String getDuration() {
        return duration;
    }

    public String getImage() {
        return image;
    }
}
