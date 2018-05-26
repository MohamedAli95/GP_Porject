package com.example.shika.boo;

/**
 * Created by Shika on 06/03/2018.
 */

public class Data_Model {

    // Getter and Setter model for recycler view items
    private String title;
    private String ofer;
    private String duration;
    private int image;

    public Data_Model(String title,String ofer,String duration, int image) {

        this.title = title;
        this.ofer = ofer;
        this.duration = duration;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getBanner() {
        return ofer;
    }
    public String getDuration() {
        return duration;
    }

    public int getImage() {
        return image;
    }
}
