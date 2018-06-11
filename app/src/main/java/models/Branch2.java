package models;

public class Branch2 {

    private String Offer_image;
    private String 	StartDate;
    private String EndDate;
    private String Title;
    private int points;

    public Branch2(String offer_image, String startDate, String endDate, String title, int points) {
        Offer_image = offer_image;
        StartDate = startDate;
        EndDate = endDate;
        Title = title;
        this.points = points;
    }

    public String getOffer_image() {
        return Offer_image;
    }

    public String getStartDate() {
        return StartDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public String getTitle() {
        return Title;
    }

    public int getPoints() {
        return points;
    }
}
