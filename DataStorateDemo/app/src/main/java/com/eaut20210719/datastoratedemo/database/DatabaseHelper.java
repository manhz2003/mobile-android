package com.eaut20210719.datastoratedemo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyDatabase.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createHangTable = "CREATE TABLE Hang (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenHang TEXT," +
                "soLuong INTEGER," +
                "gia DOUBLE)";

        String createKhachHangTable = "CREATE TABLE KhachHang (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenKhachHang TEXT," +
                "soDienThoai TEXT," +
                "diaChi TEXT)";

        String createNhanVienTable = "CREATE TABLE NhanVien (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenNhanVien TEXT," +
                "chucVu TEXT)";

        db.execSQL(createHangTable);
        db.execSQL(createKhachHangTable);
        db.execSQL(createNhanVienTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Hang");
        db.execSQL("DROP TABLE IF EXISTS KhachHang");
        db.execSQL("DROP TABLE IF EXISTS NhanVien");
        onCreate(db);
    }
}
