package com.example.samsung.fastfood.Pojo;

import android.widget.ImageView;

/**
 * Created by SAMSUNG on 11/22/2017.
 */

public class OrderDetail {

    String productName;
    int quantity;
    int price;
    String variant;
    int total;
    int secretId;
    String date;
    String user;
    String U_name;
    String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getU_name() {
        return U_name;
    }

    public void setU_name(String u_name) {
        U_name = u_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getSecretId() {
        return secretId;
    }

    public void setSecretId(int secretId) {
        this.secretId = secretId;
    }

    public OrderDetail(String productName, int quantity, int price, String variant, int secretId,String image) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.variant = variant;
        this.secretId=secretId;
        this.total = price*quantity;
        this.image=image;

    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", variant='" + variant + '\'' +
                ", total=" + total +
                ", secretId=" + secretId +
                '}';
    }
}
