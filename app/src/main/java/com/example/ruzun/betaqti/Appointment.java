package com.example.ruzun.betaqti;

public class Appointment {
    String id;
    String content;
    String date;

    Appointment(){

    }
    Appointment(String id, String content, String date){
        this.id = id;
        this.content = content;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
