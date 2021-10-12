package com.example.samsung.fastfood.Pojo;

/**
 * Created by SAMSUNG on 11/9/2017.
 */

public class ItemVariant
{
    private String price;

    private String variant;

    public String getPrice ()
    {
        return price;
    }

    public void setPrice (String price)
    {
        this.price = price;
    }

    public String getVariant ()
    {
        return variant;
    }

    public void setVariant (String variant)
    {
        this.variant = variant;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [price = "+price+", variant = "+variant+"]";
    }
}

