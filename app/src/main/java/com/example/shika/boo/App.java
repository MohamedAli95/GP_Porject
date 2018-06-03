package com.example.shika.boo;

/**
 * Created by delaroy on 2/10/17.
 */
public class App {

    public String getPlaceimage() {
        return placeimage;
    }

    public void setPlaceimage(String placeimage) {
        this.placeimage = placeimage;
    }

    private String  placeimage;
    private String mName;
    private String mRating;
    private Integer categoryid;

    public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }



    public App(String name, String  placeimage, String rating,Integer categoryid){
        mName = name;
        this.placeimage = placeimage;
        mRating = rating;
        this.categoryid=categoryid;
    }

    public String getRating(){ return mRating; }

    public String getName() { return mName; }

}
