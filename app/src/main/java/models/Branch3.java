package models;

public class Branch3 {
    private String Title;
    private int No_OF_points;
    private String Reward_image;
    private String StartDate;
    private String EndDate;

    public Branch3(String title, int no_OF_points, String reward_image, String startDate, String endDate) {
        Title = title;
        No_OF_points = no_OF_points;
        Reward_image = reward_image;
        StartDate = startDate;
        EndDate = endDate;
    }

    public String getTitle() {
        return Title;
    }

    public int getNo_OF_points() {
        return No_OF_points;
    }

    public String getReward_image() {
        return Reward_image;
    }

    public String getStartDate() {
        return StartDate;
    }

    public String getEndDate() {
        return EndDate;
    }
}
