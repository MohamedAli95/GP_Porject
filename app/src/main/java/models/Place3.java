package models;

public class Place3 {
    private int Menu_id;
    private String 	Menu_Title;
    private double 	Menu_Price;
    private String Menu_Image;

    public Place3(int menu_id, String menu_Title, double menu_Price, String menu_Image) {
        Menu_id = menu_id;
        Menu_Title = menu_Title;
        Menu_Price = menu_Price;
        Menu_Image = menu_Image;
    }

    public int getMenu_id() {
        return Menu_id;
    }

    public String getMenu_Title() {
        return Menu_Title;
    }

    public double getMenu_Price() {
        return Menu_Price;
    }

    public String getMenu_Image() {
        return Menu_Image;
    }
}
