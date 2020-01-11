package com.example.lostandfound.Model.ChatPOJO;

public class Chat
{
    public String mMessage;
    public Boolean mCurrentUser;
    public Boolean isPhoto;
    public String date;
    public String month;
    public String year;
    public String hour;
    public String minute;

    public Chat(String mMessage, Boolean mUser) {
        this.mMessage = mMessage;
        this.mCurrentUser = mUser;
    }

    public Boolean getPhoto() {
        return isPhoto;
    }

    public void setPhoto(Boolean photo) {
        isPhoto = photo;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public Boolean getmUser() {
        return mCurrentUser;
    }

    public void setmUser(Boolean mUser) {
        this.mCurrentUser = mUser;
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
}
