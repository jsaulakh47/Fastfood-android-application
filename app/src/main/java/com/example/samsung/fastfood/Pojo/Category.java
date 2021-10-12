package com.example.samsung.fastfood.Pojo;

import java.util.ArrayList;

/**
 * Created by SAMSUNG on 11/24/2017.
 */

public class Category {
    private String category_name;
    private ArrayList<Items> items;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Items> getItems() {
        return items;
    }

    public void setItems(ArrayList<Items> items) {
        this.items = items;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
