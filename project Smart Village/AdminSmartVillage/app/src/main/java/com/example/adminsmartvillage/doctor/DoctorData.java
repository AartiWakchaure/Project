package com.example.adminsmartvillage.doctor;

public class DoctorData {
    private String name, mobileNumber, image, key;

    public DoctorData() {
    }

    public DoctorData(String name, String mobileNumber, String image, String key) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.image = image;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
