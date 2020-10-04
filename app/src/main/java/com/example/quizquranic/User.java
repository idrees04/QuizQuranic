package com.example.quizquranic;

public class User {

    private String preferredName_,token_,errorMessage_;
    private int userId_;
    boolean isSuccess_;

    public User(String preferredName, String token, int userId,boolean isSuccess,  String errorMessage) {

        this.preferredName_=preferredName;
        this.token_=token;
        this.userId_=userId;
        this.isSuccess_=isSuccess;
        this.errorMessage_=errorMessage;
    }

    public String getPreferredName_() {
        return preferredName_;
    }

    public void setPreferredName_(String preferredName_) {
        this.preferredName_ = preferredName_;
    }

    public String getToken_() {
        return token_;
    }

    public void setToken_(String token_) {
        this.token_ = token_;
    }


    public int getUserId_() {
        return userId_;
    }

    public void setUserId_(int userId_) {
        this.userId_ = userId_;
    }

    public boolean isSuccess_() {
        return isSuccess_;
    }

    public void setSuccess_(boolean success_) {
        isSuccess_ = success_;
    }

    public String getErrorMessage_() {
        return errorMessage_;
    }

    public void setErrorMessage_(String errorMessage_) {
        this.errorMessage_ = errorMessage_;
    }

}