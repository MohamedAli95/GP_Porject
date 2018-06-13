package models;

public class SearchPlace {
    private int Place_ID;
    private String 	PLaceName;
    private String Place_LogoPhoto;

    public SearchPlace(int place_ID, String PLaceName, String place_LogoPhoto) {
        Place_ID = place_ID;
        this.PLaceName = PLaceName;
        Place_LogoPhoto = place_LogoPhoto;
    }

    public int getPlace_ID() {
        return Place_ID;
    }

    public String getPLaceName() {
        return PLaceName;
    }

    public String getPlace_LogoPhoto() {
        return Place_LogoPhoto;
    }
}
