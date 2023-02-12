package com.example.smartvillage.news;

public class NewsData {
    String image, title, description, date, time, key;

    public NewsData() {

    }

    public NewsData(String image, String title, String description, String date, String time, String key) {
        this.image = image;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
