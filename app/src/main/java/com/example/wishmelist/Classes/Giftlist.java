package com.example.wishmelist.Classes;

import java.util.Date;

public class Giftlist {

    private String eventName;
    private Date eventDate;
    private String adress;
//    private ArrayList<Gift> gift;

    public Giftlist(String name, Date date, String adress/*arrayList<Gift> */) {
        this.eventName = name;
        this.eventDate = date;
        this.adress = adress;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
