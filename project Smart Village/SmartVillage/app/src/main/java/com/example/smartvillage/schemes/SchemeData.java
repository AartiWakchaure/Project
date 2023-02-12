package com.example.smartvillage.schemes;

public class SchemeData {
    private String image, name, startDate, endDate, link, description,date, time, key;

    public SchemeData() {
    }

    public SchemeData(String image, String name, String startDate, String endDate, String link, String description, String date, String time, String key) {
        this.image = image;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.link = link;
        this.description = description;
        this.date = date;
        this.time = time;
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
