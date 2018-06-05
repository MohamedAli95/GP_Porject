package models;

public class placeCategorypage {
    float Rate= 0 ;
    String logoUrl;
    String Placename;
    Integer category_id;
    Integer place_id;

    public Integer getPlace_id() {
        return place_id;
    }

    public void setPlace_id(Integer place_id) {
        this.place_id = place_id;
    }



    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }




    public float getRate() {
        return Rate;
    }

    public void setRate(float rate) {
        Rate = rate;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getPlacename() {
        return Placename;
    }

    public void setPlacename(String placename) {
        Placename = placename;
    }
}
