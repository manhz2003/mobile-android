package com.eaut20210719.quanlydonhang;

import java.io.Serializable;

public class Order implements Serializable {
    private String productName;
    private int quantity;
    private double price;
    private String productType;
    private boolean giftWrap;
    private boolean warranty;
    private String deliveryType;
    private double totalPrice;

    // Constructor
    public Order(String productName, int quantity, double price, String productType, boolean giftWrap, boolean warranty, String deliveryType, double totalPrice) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.productType = productType;
        this.giftWrap = giftWrap;
        this.warranty = warranty;
        this.deliveryType = deliveryType;
        this.totalPrice = totalPrice;
    }

    // Getter and Setter methods
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public boolean isGiftWrap() {
        return giftWrap;
    }

    public void setGiftWrap(boolean giftWrap) {
        this.giftWrap = giftWrap;
    }

    public boolean isWarranty() {
        return warranty;
    }

    public void setWarranty(boolean warranty) {
        this.warranty = warranty;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
