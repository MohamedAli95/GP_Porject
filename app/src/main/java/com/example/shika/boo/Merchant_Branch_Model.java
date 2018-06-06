package com.example.shika.boo;

public class Merchant_Branch_Model {

    private String name;
    private int branch_id;
    private int place_id;


    public Merchant_Branch_Model( String title, int braid , int plaid) {
        this.name = title;
        this.branch_id = braid;
        this.place_id = plaid;
    }


    public String getTitle() {
        return name;
    }
    public int getBranch_id() {
        return branch_id;
    }
    public int getPlace_id() {
        return place_id;
    }
}