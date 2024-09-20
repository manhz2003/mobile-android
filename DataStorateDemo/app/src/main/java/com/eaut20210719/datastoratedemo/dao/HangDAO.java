package com.eaut20210719.datastoratedemo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.eaut20210719.datastoratedemo.database.DatabaseHelper;
import com.eaut20210719.datastoratedemo.models.Hang;

import java.util.ArrayList;
import java.util.List;

public class HangDAO {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public HangDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Thêm hàng
    public long insertHang(Hang hang) {
        ContentValues values = new ContentValues();
        values.put("tenHang", hang.getTenHang());
        values.put("soLuong", hang.getSoLuong());
        values.put("gia", hang.getGia());
        return db.insert("Hang", null, values);
    }

    // Cập nhật hàng
    public int updateHang(Hang hang) {
        ContentValues values = new ContentValues();
        values.put("tenHang", hang.getTenHang());
        values.put("soLuong", hang.getSoLuong());
        values.put("gia", hang.getGia());
        return db.update("Hang", values, "id=?", new String[]{String.valueOf(hang.getId())});
    }

    // Xóa hàng
    public int deleteHang(int id) {
        return db.delete("Hang", "id=?", new String[]{String.valueOf(id)});
    }

    // Lấy tất cả hàng
    public List<Hang> getAllHang() {
        List<Hang> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.query("Hang", new String[]{"id", "tenHang", "soLuong", "gia"},
                    null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Hang hang = new Hang();
                    hang.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    hang.setTenHang(cursor.getString(cursor.getColumnIndexOrThrow("tenHang")));
                    hang.setSoLuong(cursor.getInt(cursor.getColumnIndexOrThrow("soLuong")));
                    hang.setGia(cursor.getDouble(cursor.getColumnIndexOrThrow("gia")));
                    list.add(hang);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }
}
