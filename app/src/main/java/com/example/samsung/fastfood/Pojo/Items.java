package com.example.samsung.fastfood.Pojo;

import java.util.ArrayList;

/**
 * Created by SAMSUNG on 11/9/2017.
 */

public class Items
{
    private ArrayList<ItemVariant> item_variant;

    private String item_name;

    private String image;


    public ArrayList<ItemVariant> getItem_variant() {
        return item_variant;
    }

    public void setItem_variant(ArrayList<ItemVariant> item_variant) {
        this.item_variant = item_variant;
    }

    public String getItem_name ()
    {
        return item_name;
    }

    public void setItem_name (String item_name)
    {
        this.item_name = item_name;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [item_variant = "+item_variant+", item_name = "+item_name+", image = "+image+"]";
    }
}


