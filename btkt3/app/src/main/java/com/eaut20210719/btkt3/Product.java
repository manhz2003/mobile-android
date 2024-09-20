package com.eaut20210719.btkt3;

public class Product {
    private String name;
    private double unitPrice;
    private int quantity;
    private double totalPrice;
    private double vat;
    private double totalAmount;

    public Product(String name, double unitPrice, int quantity) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        calculatePrices();
    }

    private void calculatePrices() {
        this.totalPrice = unitPrice * quantity;
        this.vat = totalPrice * 0.1;
        this.totalAmount = totalPrice + vat;
    }

    public String getName() {
        return name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getVat() {
        return vat;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "Sản phẩm: " + name + "\n" +
                "Thành tiền: " + totalPrice + "\n" +
                "VAT (10%): " + vat + "\n" +
                "Tổng tiền: " + totalAmount;
    }

    // Phương thức setter để thiết lập giá trị cho các thuộc tính
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
