package com.example.ruzun.betaqti;

public class Request {

    private String requestName;
    private String requestDate;
    private String requester;
    private String id;
    private String email;
    private boolean state = false;
    Request(){

    }
    Request(String requestName, String requestDate, String requester, String email){
        this.requestName = requestName;
        this.requestDate = requestDate;
        this.requester = requester;
        this.email=email;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestName() {
        return requestName;

    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getRequester() {
        return requester;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean getState() {
        return state;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}

