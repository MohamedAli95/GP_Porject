package models;

public class Place1 {
    private String Place_LogoPhoto;
    private String 	PLaceName;
    private String Name;
    private int No_OF_points;

    public Place1(String place_LogoPhoto, String PLaceName, String name, int no_OF_points) {
        Place_LogoPhoto = place_LogoPhoto;
        this.PLaceName = PLaceName;
        Name = name;
        No_OF_points = no_OF_points;
    }

    public String getPlace_LogoPhoto() {
        return Place_LogoPhoto;
    }

    public String getPLaceName() {
        return PLaceName;
    }

    public String getName() {
        return Name;
    }

    public int getNo_OF_points() {
        return No_OF_points;
    }
}
