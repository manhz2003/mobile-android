package com.eaut20210719.datastoratedemo.models;

public class Hang {
    private int id;
    private String tenHang;
    private int soLuong;
    private double gia;

    // Constructor không tham số
    public Hang() {}

    // Constructor có tham số
    public Hang(int id, String tenHang, int soLuong, double gia) {
        this.id = id;
        this.tenHang = tenHang;
        this.soLuong = soLuong;
        this.gia = gia;
    }

    // Getter và Setter cho thuộc tính id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter và Setter cho thuộc tính tenHang
    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    // Getter và Setter cho thuộc tính soLuong
    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    // Getter và Setter cho thuộc tính gia
    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }
}
