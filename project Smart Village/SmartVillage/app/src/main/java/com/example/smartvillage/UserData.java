package com.example.smartvillage;

public class UserData
{
    private String name, email, mobileNumber, age, gender, occupation, maritalStatus, key;

    public UserData() {
    }

    public UserData(String name, String email, String mobileNumber, String age, String gender, String occupation, String maritalStatus, String key) {
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.age = age;
        this.gender = gender;
        this.occupation = occupation;
        this.maritalStatus = maritalStatus;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
