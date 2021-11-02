package com.example.wishmelist.Classes;

public class GiftItem {

    private String itemName;
    private String link;
    private int itemPrice;

    public GiftItem(String item, String link, int price) {
        this.itemName = item;
        this.link = link;
        this.itemPrice = price;
    }

    public GiftItem(String item, int price) {
        this.itemName = item;
        this.itemPrice = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }
}
