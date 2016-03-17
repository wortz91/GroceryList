package com.grocerylist;

/**
 * Created by Nicholas on 2/1/2016.
 *
 * this will contain the information parsed from the database
 */
public class ItemData {

    private int itemID;
    private String itemName;
    private String itemUnitType;
    private String itemDescription;
    private double itemPrice;
    private int itemCount;
    private String itemCategory;

    public ItemData() {

    }

    // int imageUrl
    public ItemData(int itemID, String itemName, String itemUnitType, String itemDescription, double itemPrice, int itemCount, String itemCategory){

        this.itemID = itemID;
        this.itemName = itemName;
        this.itemUnitType = itemUnitType;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemCount = itemCount;
        this.itemCategory = itemCategory;
    }
    // getters & setters

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemUnitType() {
        return itemUnitType;
    }

    public void setItemUnitType(String itemUnitType) {
        this.itemUnitType = itemUnitType;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    @Override
    public String toString() {
        return "ItemID:" + getItemID() + "ItemName: " + getItemName() + " ItemCategory: " + getItemCategory();
    }
}
