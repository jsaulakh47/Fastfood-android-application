package com.example.samsung.fastfood.Pojo;

import android.location.Address;

/**
 * Created by SAMSUNG on 12/6/2017.
 */

public class Proceed_Buy {
    String User_name;
    String Order_Date;

    String City;
    String Address;

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public String getOrder_Date() {
        return Order_Date;
    }

    public void setOrder_Date(String order_Date) {
        Order_Date = order_Date;
    }

    public Proceed_Buy(String user_name, String order_Date,String city,String address) {
        User_name = user_name;
        Order_Date = order_Date;
        City=city;
        Address=address;




    }
}
