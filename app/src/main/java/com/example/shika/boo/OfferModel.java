package com.example.shika.boo;

/**
 * Created by Shika on 06/03/2018.
 */

public class OfferModel {

    // Getter and Setter model for recycler view items
    private int id;
    private String title;
    private int points;
    private String duration;
    private String duration1;
    private String image;
    private double Price;

    public OfferModel(int id,String title,int points,String duration1,String duration, String image,double Price) {
         this.id = id;
        this.title = title;
        this.points = points;
        this.duration1=duration1;
        this.duration = duration;
        this.image = image;
        this.Price = Price;
    }

    public String getTitle() {
        return title;
    }

    public int getBanner() {
        return points;
    }
    public int getID() {
        return id;
    }
    public String getDuration() {
        return duration;
    }
    public String getDuration1() {
        return duration1;
    }

    public String getImage() {
        return image;
    }
    public double getPrice() {
        return Price;
    }

}
