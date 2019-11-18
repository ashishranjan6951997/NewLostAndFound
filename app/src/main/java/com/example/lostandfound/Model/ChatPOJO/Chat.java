package com.example.lostandfound.Model.ChatPOJO;

public class Chat
{
    public String mMessage;
    public Boolean mCurrentUser;

    public Chat(String mMessage, Boolean mUser) {
        this.mMessage = mMessage;
        this.mCurrentUser = mUser;
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
}
