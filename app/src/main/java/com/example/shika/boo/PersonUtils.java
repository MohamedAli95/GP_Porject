package com.example.shika.boo;


public class PersonUtils {

    private String personFirstName;
    private String personLastName;
    private String jobProfile;
    private String img;

    public PersonUtils(String firstname, String lastname, String jobprofile, String pik) {
        this.personFirstName = firstname;
        this.personLastName = lastname;
        this.jobProfile = jobprofile;
        this.img = pik;
    }

    public String getPersonFirstName() {
        return personFirstName;
    }

    public void setPersonFirstName(String personFirstName) {
        this.personFirstName = personFirstName;
    }

    public String getPersonLastName() {
        return personLastName;
    }

    public void setPersonLastName(String personLastName) {
        this.personLastName = personLastName;
    }

    public String getJobProfile() {
        return jobProfile;
    }

    public void setJobProfile(String jobProfile) {
        this.jobProfile = jobProfile;
    }

    public String getProductimage() {
        return img;
    }

    public void setProductimage(String img) {
        this.img = img;
    }
}