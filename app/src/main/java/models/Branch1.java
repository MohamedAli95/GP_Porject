package models;

public class Branch1 {
    private String Place_LogoPhoto;
    private String 	PLaceName;
    private String Branch_name;
    private Double latitude;
    private Double longitude;
    private int RewardSystemAvailabilty;

    public Branch1(String place_LogoPhoto, String PLaceName, String branch_name, Double latitude, Double longitude, int rewardSystemAvailabilty) {
        Place_LogoPhoto = place_LogoPhoto;
        this.PLaceName = PLaceName;
        Branch_name = branch_name;
        this.latitude = latitude;
        this.longitude = longitude;
        RewardSystemAvailabilty = rewardSystemAvailabilty;
    }

    public String getPlace_LogoPhoto() {
        return Place_LogoPhoto;
    }

    public String getPLaceName() {
        return PLaceName;
    }

    public String getBranch_name() {
        return Branch_name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public int getRewardSystemAvailabilty() {
        return RewardSystemAvailabilty;
    }
}
