package models;

public class UserInterests {
    String Categorytitle;
    Integer category_id;


    public int getInteriststatus() {
        return interiststatus;
    }

    public void setInteriststatus(int interiststatus) {
        this.interiststatus = interiststatus;
    }

    int interiststatus;

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }


    public String getCategorytitle() {
        return Categorytitle;
    }

    public void setCategorytitle(String categorytitle) {
        Categorytitle = categorytitle;
    }
}
