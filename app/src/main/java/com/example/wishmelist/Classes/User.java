package com.example.wishmelist.Classes;

import java.util.ArrayList;

public class User {

    private String name;
    private String password;
    private String email;
    private ArrayList<Giftlist> giftlists;

    public User() {
        //Default empty constructor
    }

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Giftlist> getGiftlists() {
        return giftlists;
    }


    public void addGiftlist(Giftlist list) {
        giftlists.add(list);
    }
}
