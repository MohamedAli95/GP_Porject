package com.example.shika.boo;

import java.io.Serializable;

public class YoutuberModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String Title;
    private int points;
    private String From_date;
    private String To_date;
    private String Reward_image;

    public YoutuberModel() {
    }

    public YoutuberModel(int id, String Title ,int points, String From_date , String To_date , String Reward_image) {
        this.id = id;
        this.Title = Title;
        this.points=points;
this.From_date = From_date;
this.To_date = To_date;
this.Reward_image = Reward_image;
    }

     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Title;
    }

    public void setName(String Title) {
        this.Title = Title;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int id) {
        this.points = points;
    }

     public String getSdate() {
        return From_date;
    }

    public void setSdate(String From_date) {
        this.From_date = From_date;
    }

     public String getEdate() {
        return To_date;
    }

    public void setEdate(String To_date) {
        this.To_date = To_date;
    }


     public String getImage() {
        return Reward_image;
    }

    public void setImage(String Reward_image) {
        this.Reward_image = Reward_image;
    }
}
