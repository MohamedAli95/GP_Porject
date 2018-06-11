package models;

public class favorits {
    int place_id;
    String placename;
    String placelogo;
    int favoritid;

    public int getFavoritid() {
        return favoritid;
    }

    public void setFavoritid(int favoritid) {
        this.favoritid = favoritid;
    }



    public int getPlace_id() {
        return place_id;
    }

    public void setPlace_id(int place_id) {
        this.place_id = place_id;
    }

    public String getPlacename() {
        return placename;
    }

    public void setPlacename(String placename) {
        this.placename = placename;
    }

    public String getPlacelogo() {
        return placelogo;
    }

    public void setPlacelogo(String placelogo) {
        this.placelogo = placelogo;
    }
}
