package com.example.shika.boo;

import java.io.Serializable;

public class YoutuberModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String startdate;
    private String enddate;
    private int image;

    public YoutuberModel() {
    }

    public YoutuberModel(String name, String sdate , String edate, int img) {
        this.name = name;
        this.startdate = sdate;
        this.enddate = edate;
        this.image = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSdate() {
        return startdate;
    }

    public void setsdate(String sdate) { this.startdate = sdate; }

    public String getedate() {
        return enddate;
    }

    public void setedate(String edate) { this.enddate = edate; }

    public int getimage() {
        return image;
    }

    public void setimage(int img) { this.image = img; }

}
