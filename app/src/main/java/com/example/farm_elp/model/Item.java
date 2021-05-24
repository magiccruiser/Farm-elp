package com.example.farm_elp.model;

import android.widget.ImageView;

import java.io.Serializable;

public class Item implements Serializable {
    private String itemName;
    private String description;
    private int quantity;
    private String itemUrl="";
    private String itemType;
    private String UID;

    //---------------------------------Constructors-----------------------------------
    public Item(){}

    public Item( String url, String name, String desc, int num,String type,String UID){
        itemUrl=url;
        itemName=name;
        description=desc;
        quantity=num;
        itemType=type;
        this.UID=UID;
    }

    //---------------------------------Getters and Setters-----------------------------------
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemImage) {
        this.itemUrl = itemImage;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemName='" + itemName + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", itemUrl='" + itemUrl + '\'' +
                ", itemType='" + itemType + '\'' +
                ", UID='" + UID + '\'' +
                '}';
    }
}
