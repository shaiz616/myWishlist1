package com.example.wishmelist.Classes;

public class GiftItem {

    private String itemName;
    private String link;
    private String itemModel;
    private String itemPrice;
    private String ItemId;

    public GiftItem(String name, String link, String model, String Price) {
        this.itemName = name;
        this.link = link;
        this.itemModel = model;
        this.itemPrice = Price;
    }
    public GiftItem(){}

    public GiftItem(String name, String model, String ItemId) {
        this.itemName = name;
        this.itemModel = model;
        this.ItemId = ItemId;
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


    public String getId(){return ItemId;};
    public void setId(String ItemId){this.ItemId= ItemId;};


    public String getItemModel() {
        return itemModel;
    }

    public void setItemModel(String itemModel) {
        this.itemModel = itemModel;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }


}
