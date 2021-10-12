package com.example.samsung.fastfood.Pojo;

import java.util.ArrayList;

/**
 * Created by SAMSUNG on 11/9/2017.
 */

public class MainData {


    private ArrayList<Category> category;

    public ArrayList<Category> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<Category> category) {
        this.category = category;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [category = "+category+"]";
    }
}