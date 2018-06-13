package com.example.shika.boo;

public class Merchant_Menu {


    public String ImageURL;
    public int placeid;
public int id;
    public Merchant_Menu(int id,String ImageURL,int placeid){
        this.ImageURL = ImageURL;
        this.placeid=placeid;
        this.id= id;
    }

    public String getImageUrl() {

        return ImageURL;
    }

    public void setImageUrl(String imageServerUrl) {

        this.ImageURL = imageServerUrl;
    }


    public int getPlaceId() {

        return placeid;
    }

    public void setPlaceId(int placeId) {

        this.placeid = placeId;
    }


    public int getId() {

        return id;
    }

    public void setId(int Id) {

        this.id = Id;
    }
}