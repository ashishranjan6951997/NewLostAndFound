package com.example.lostandfound.Model.CardPOJO;

public class Card
{
    String id;
    String desc;
    String date;
    String time;
    String location;
    String profileImageUrl;

    public Card()
    {

    }

    public Card(String id, String desc, String date, String time, String location, String profileImageUrl) {
        this.id = id;
        this.desc = desc;
        this.date = date;
        this.time = time;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
