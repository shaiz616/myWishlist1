package com.example.wishmelist.Classes;

import java.util.Date;

public class EventDetails {

    private String eventID;
    private String eventName;
    private String eventType;
    private EventDate eventDate;
    private String address;

//    private ArrayList<Gift> gift;

    //      empty constructor
    public EventDetails() {
    }

    public EventDetails(String name, String type, EventDate date, String address /*arrayList<Gift> */) {
        this.eventType = name;
        this.eventDate = date;
        this.address = address;
        this.eventType = type;
    }


    public static class EventDate {
        private int dayOfMonth;
        private int month;
        private int year;


        public EventDate(int dd, int mm, int yy) {
            this.dayOfMonth = dd;
            this.month = mm;
            this.year = yy;
        }

        public int getDayOfMonth() {
            return dayOfMonth;
        }

        public void setDayOfMonth(int dayOfMonth) {
            this.dayOfMonth = dayOfMonth;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        @Override
        public String toString() {
            return "EventDate" + dayOfMonth + "/" + month + "/" + year ;
        }
    }


    public String getEventName() { return eventName; }

    public void setEventName(String eventName) { this.eventName = eventName; }

    public void setEventID(String eventID) { this.eventID = eventID; }

    public String getEventID() { return eventID; }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public EventDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(EventDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String printEvent() {
        String str = "an event of type "
            + getEventType()
            + " has been created ";

        return str;
    }
}
