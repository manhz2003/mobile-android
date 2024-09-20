package com.eaut20210719.btkt2;

import android.os.Parcel;
import android.os.Parcelable;

public class SinhVien implements Parcelable {
    private String msv;
    private String hoTen;
    private String ngaySinh;
    private String lop;
    private float diemTrungBinh1;
    private float diemTrungBinh2;
    private float diemTrungBinh3;

    // Constructor
    public SinhVien(String msv, String hoTen, String ngaySinh, String lop, float diemTrungBinh1, float diemTrungBinh2, float diemTrungBinh3) {
        this.msv = msv;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.lop = lop;
        this.diemTrungBinh1 = diemTrungBinh1;
        this.diemTrungBinh2 = diemTrungBinh2;
        this.diemTrungBinh3 = diemTrungBinh3;
    }

    // Parcelable implementation
    protected SinhVien(Parcel in) {
        msv = in.readString();
        hoTen = in.readString();
        ngaySinh = in.readString();
        lop = in.readString();
        diemTrungBinh1 = in.readFloat();
        diemTrungBinh2 = in.readFloat();
        diemTrungBinh3 = in.readFloat();
    }

    public static final Creator<SinhVien> CREATOR = new Creator<SinhVien>() {
        @Override
        public SinhVien createFromParcel(Parcel in) {
            return new SinhVien(in);
        }

        @Override
        public SinhVien[] newArray(int size) {
            return new SinhVien[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(msv);
        dest.writeString(hoTen);
        dest.writeString(ngaySinh);
        dest.writeString(lop);
        dest.writeFloat(diemTrungBinh1);
        dest.writeFloat(diemTrungBinh2);
        dest.writeFloat(diemTrungBinh3);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters
    public String getMsv() {
        return msv;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public String getLop() {
        return lop;
    }

    public float getDiemTrungBinh1() {
        return diemTrungBinh1;
    }

    public float getDiemTrungBinh2() {
        return diemTrungBinh2;
    }

    public float getDiemTrungBinh3() {
        return diemTrungBinh3;
    }
}
