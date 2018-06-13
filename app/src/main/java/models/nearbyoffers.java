package models;

public class nearbyoffers {
    String title;
    String branch_name;
    double latitude;
    double longitude;
    String placename;
    Integer Offer_id;
    String Startdate;
    String Enddate;
    Integer No_ofpoints;
    String Category;
    int Catgoryid;

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getCatgoryid() {
        return Catgoryid;
    }

    public void setCatgoryid(int catgoryid) {
        Catgoryid = catgoryid;
    }

    public Integer getNo_ofpoints() {
        return No_ofpoints;
    }

    public void setNo_ofpoints(Integer no_ofpoints) {
        No_ofpoints = no_ofpoints;
    }



    public String getStartdate() {
        return Startdate;
    }

    public void setStartdate(String startdate) {
        Startdate = startdate;
    }

    public String getEnddate() {
        return Enddate;
    }

    public void setEnddate(String enddate) {
        Enddate = enddate;
    }

    public Integer getOffer_id() {
        return Offer_id;
    }

    public void setOffer_id(Integer offer_id) {
        Offer_id = offer_id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPlacename() {
        return placename;
    }

    public void setPlacename(String placename) {
        this.placename = placename;
    }
}
