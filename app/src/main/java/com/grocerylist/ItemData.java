package com.grocerylist;

/**
 * Created by Nicholas on 2/1/2016.
 */
public class ItemData {


    private String title;
    private int imageUrl;

    // int imageUrl
    public ItemData(String title){

        this.title = title;
        //this.imageUrl = imageUrl;
    }
    // getters & setters

    public String getTitle() {
        return title;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
