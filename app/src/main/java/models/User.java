package models;

import java.util.Date;

public  class User {


    private int id ;
    private String password;
    private String email;
    private String Name;
    private Date lastlogin;
    private String profile_Picture;
    private int age;
    private String Phone;
    private String gender;
    private String Setting;

    public String getSetting() {
        return Setting;
    }

    public void setSetting(String setting) {
        Setting = setting;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Date getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(Date lastlogin) {
        this.lastlogin = lastlogin;
    }

    public String getProfile_Picture() {
        return profile_Picture;
    }

    public void setProfile_Picture(String profile_Picture) {
        this.profile_Picture = profile_Picture;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
