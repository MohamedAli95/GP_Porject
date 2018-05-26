package com.example.shika.boo;

/**
 * Created by Shika on 06/03/2018.
 */
public class ItemObjects {
    private String name;
    private String moda;
    private String detail;
    private int photo;

    public ItemObjects(String name,String moda , String detail, int photo) {
        this.name = name;
        this.moda = moda;
        this.detail = detail;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModa() { return moda; }

    public void setModa(String moda) {
        this.moda = moda;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
