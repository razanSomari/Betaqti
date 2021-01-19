package com.example.ruzun.betaqti;

public class User {
    private String email;
    private String password;
    private String ID;
    private String IDExpiryDay;
    private String IDType;
    private String name;
    private String UserID;

    public User(){

    }
    public User(String email, String password, String name, String ID, String IDExpiryDay, String IDType){
        this.email = email;
        this.ID = ID;
        this.password = password;
        this.IDExpiryDay = IDExpiryDay;
        this.IDType = IDType;
        this.name = name;

    }


    public String getEmail() {
        return email;
    }

    public String getID() {
        return ID;
    }

    public String getIDExpiryDay() {
        return IDExpiryDay;
    }

    public String getIDType() {
        return IDType;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setIDExpiryDay(String IDExpiryDay) {
        this.IDExpiryDay = IDExpiryDay;
    }

    public void setIDType(String IDType) {
        this.IDType = IDType;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String toString(){
        return "اسم الطالبة:"+name+"<br /> "+"الرقم التدريبي: "+ID+"<br /> "+"الايميل: "+email+"<br /> "+"نوع الهوية:"+IDType+"<br /> "+"تاريخ صلاحية الهوية:"+IDExpiryDay+"<br /> ";
    }
}

