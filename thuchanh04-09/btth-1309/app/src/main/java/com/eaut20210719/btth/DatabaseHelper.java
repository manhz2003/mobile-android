package com.eaut20210719.btth;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "AppDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String TABLE_PRODUCTS = "products";

    // Các cột cho bảng users
    private static final String USER_ID = "user_id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    // Các cột cho bảng products
    private static final String PRODUCT_ID = "product_id";
    private static final String PRODUCT_NAME = "name";
    private static final String PRODUCT_DESCRIPTION = "description";
    private static final String PRODUCT_IMAGE_URL = "image_url";

    // Tạo bảng users
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + USERNAME + " TEXT,"
            + PASSWORD + " TEXT" + ")";

    // Tạo bảng products
    private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCTS + "("
            + PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + PRODUCT_NAME + " TEXT,"
            + PRODUCT_DESCRIPTION + " TEXT,"
            + PRODUCT_IMAGE_URL + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_PRODUCTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    // Thêm người dùng mới
    public void addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME, username);
        values.put(PASSWORD, password);
        db.insert(TABLE_USERS, null, values);
        db.close(); // Đóng kết nối cơ sở dữ liệu sau khi thêm
    }

    // Kiểm tra thông tin người dùng (Đăng nhập)
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + USERNAME + "=? AND " + PASSWORD + "=?", new String[]{username, password});
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return true; // Người dùng tồn tại
        }
        cursor.close();
        db.close();
        return false; // Người dùng không tồn tại
    }

    // Thêm sản phẩm mới
    public void addProduct(String name, String description, String imageUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME, name);
        values.put(PRODUCT_DESCRIPTION, description);
        values.put(PRODUCT_IMAGE_URL, imageUrl);
        db.insert(TABLE_PRODUCTS, null, values);
        db.close(); // Đóng kết nối sau khi thêm
    }

    // Lấy danh sách tất cả các sản phẩm
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Lặp qua tất cả các hàng và thêm vào danh sách sản phẩm
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndexOrThrow(PRODUCT_ID)));
                product.setName(cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_NAME)));
                product.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_DESCRIPTION)));
                product.setImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_IMAGE_URL)));

                products.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return products;
    }

    // Kiểm tra xem username đã tồn tại hay chưa
    public boolean isUserExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + USERNAME + "=?", new String[]{username});

        boolean exists = cursor.getCount() > 0;  // Kiểm tra nếu có bất kỳ kết quả nào trả về từ câu truy vấn
        cursor.close();
        db.close();
        return exists;
    }


    // Lớp mô hình sản phẩm (Product Model Class)
    public class Product {
        private int id;
        private String name;
        private String description;
        private String imageUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}
