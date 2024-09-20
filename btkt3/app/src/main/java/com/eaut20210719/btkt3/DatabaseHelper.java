package com.eaut20210719.btkt3;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Các khai báo hiện có
    private static final String DATABASE_NAME = "product.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "products";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_UNIT_PRICE = "unit_price";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_TOTAL_PRICE = "total_price";
    private static final String COLUMN_VAT = "vat";
    private static final String COLUMN_TOTAL_AMOUNT = "total_amount";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_UNIT_PRICE + " REAL, "
                + COLUMN_QUANTITY + " INTEGER, "
                + COLUMN_TOTAL_PRICE + " REAL, "
                + COLUMN_VAT + " REAL, "
                + COLUMN_TOTAL_AMOUNT + " REAL" + ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_UNIT_PRICE, product.getUnitPrice());
        values.put(COLUMN_QUANTITY, product.getQuantity());
        values.put(COLUMN_TOTAL_PRICE, product.getTotalPrice());
        values.put(COLUMN_VAT, product.getVat());
        values.put(COLUMN_TOTAL_AMOUNT, product.getTotalAmount());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // Phương thức để lấy tất cả sản phẩm từ cơ sở dữ liệu
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                @SuppressLint("Range") double unitPrice = cursor.getDouble(cursor.getColumnIndex(COLUMN_UNIT_PRICE));
                @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY));
                @SuppressLint("Range") double totalPrice = cursor.getDouble(cursor.getColumnIndex(COLUMN_TOTAL_PRICE));
                @SuppressLint("Range") double vat = cursor.getDouble(cursor.getColumnIndex(COLUMN_VAT));
                @SuppressLint("Range") double totalAmount = cursor.getDouble(cursor.getColumnIndex(COLUMN_TOTAL_AMOUNT));

                Product product = new Product(name, unitPrice, quantity);
                product.setTotalPrice(totalPrice);
                product.setVat(vat);
                product.setTotalAmount(totalAmount);

                products.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return products;
    }
}
