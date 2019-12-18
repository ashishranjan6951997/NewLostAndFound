package com.example.lostandfound.Model.CardPOJO;

public class Card
{

    String id;
    String name;
    String desc;
    String hour;
    String minute;
    String date;
    String month;
    String year;
    String location;
    String profileImageUrl;
    String postImageUri;
    String format;

    public Card()
    {

    }

    public Card(String id, String desc, String location, String profileImageUrl) {
        this.id = id;
        this.desc = desc;
        this.location = location;
        this.profileImageUrl = profileImageUrl;
    }

    public Card(String id, String desc, String profileImageUrl) {
        this.id = id;
        this.desc = desc;
        this.profileImageUrl = profileImageUrl;
     //   this.location=location;
    }

    public Card(String key, String name)
    {
        this.id = key;
        this.desc = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }


    public String getPostImageUri() {
        return postImageUri;
    }

    public void setPostImageUri(String postImageUri) {
        this.postImageUri = postImageUri;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
