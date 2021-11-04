package com.example.wishmelist.Classes;

public class GiftItem {

    private String itemName;
    private String link;
    private String itemPrice;
    private String ItemId;

    public GiftItem(String item, String link, String price, int ItemId) {
        this.itemName = item;
        this.link = link;
        this.itemPrice = price;
        ItemId = ItemId;
    }
    public GiftItem(){}
    public GiftItem(String item, String price, String ItemId) {
        this.itemName = item;
        this.itemPrice = price;
        ItemId = ItemId;
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
    public String getId(){return ItemId;};
    public void setId(){this.ItemId= ItemId;};

    public void setLink(String link) {
        this.link = link;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }
}
