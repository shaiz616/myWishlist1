package com.example.wishmelist.Classes;

public class GiftItem {

    private String itemName;
    private String link;
    private String itemModel;
    private String ItemId;

    public GiftItem(String item, String link, String model, String ItemId) {
        this.itemName = item;
        this.link = link;
        this.itemModel = model;
        this.ItemId = ItemId;
    }
    public GiftItem(){}
    public GiftItem(String item, String model, String ItemId) {
        this.itemName = item;
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
    public String getId(){return ItemId;};
    public void setId(){this.ItemId= ItemId;};

    public void setLink(String link) {
        this.link = link;
    }

    public String getItemModel() {
        return itemModel;
    }

    public void setItemModel(String itemModel) {
        this.itemModel = itemModel;
    }
}
