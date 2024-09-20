package com.eaut20210719.thuchanh1609;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "AppDatabase";
    private static final int DATABASE_VERSION = 5;

    // Bảng người dùng
    private static final String TABLE_USERS = "users";
    private static final String USER_ID = "user_id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    // Bảng sản phẩm
    private static final String TABLE_PRODUCTS = "products";
    private static final String PRODUCT_ID = "product_id";
    private static final String PRODUCT_NAME = "name";
    private static final String PRODUCT_DESCRIPTION = "description";
    private static final String PRODUCT_PRICE = "price";

    // Bảng giỏ hàng
    private static final String TABLE_CART = "cart";
    private static final String CART_ID = "cart_id";
    private static final String CART_PRODUCT_ID = "product_id";
    private static final String CART_QUANTITY = "quantity";

    // Bảng đánh giá
    private static final String TABLE_REVIEWS = "reviews";
    private static final String REVIEW_ID = "review_id";
    private static final String REVIEW_PRODUCT_ID = "product_id";
    private static final String REVIEW_CONTENT = "content";
    private static final String REVIEW_DATE = "date";
    private static final String REVIEW_TIME = "time";
    private static final String REVIEW_RATING = "rating";
    private static final String REVIEW_CATEGORY = "category";

    // Tạo bảng người dùng
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + USERNAME + " TEXT,"
            + PASSWORD + " TEXT" + ")";

    // Tạo bảng sản phẩm
    private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCTS + "("
            + PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + PRODUCT_NAME + " TEXT,"
            + PRODUCT_DESCRIPTION + " TEXT,"
            + PRODUCT_PRICE + " REAL" + ")";

    // Tạo bảng giỏ hàng
    private static final String CREATE_TABLE_CART = "CREATE TABLE " + TABLE_CART + "("
            + CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CART_PRODUCT_ID + " INTEGER,"
            + CART_QUANTITY + " INTEGER,"
            + "FOREIGN KEY(" + CART_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + PRODUCT_ID + "))";

    // Tạo bảng đánh giá
    private static final String CREATE_TABLE_REVIEWS = "CREATE TABLE " + TABLE_REVIEWS + "("
            + REVIEW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + REVIEW_PRODUCT_ID + " INTEGER,"
            + REVIEW_CONTENT + " TEXT,"
            + REVIEW_DATE + " TEXT,"
            + REVIEW_TIME + " TEXT,"
            + REVIEW_RATING + " REAL,"
            + REVIEW_CATEGORY + " TEXT,"
            + "FOREIGN KEY(" + REVIEW_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + PRODUCT_ID + "))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_PRODUCTS);
        db.execSQL(CREATE_TABLE_CART);
        db.execSQL(CREATE_TABLE_REVIEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEWS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
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

    // Kiểm tra xem username đã tồn tại hay chưa
    public boolean isUserExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + USERNAME + "=?", new String[]{username});

        boolean exists = cursor.getCount() > 0;  // Kiểm tra nếu có bất kỳ kết quả nào trả về từ câu truy vấn
        cursor.close();
        db.close();
        return exists;
    }

    // Thêm sản phẩm mới
    public void addProduct(String name, String description, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME, name);
        values.put(PRODUCT_DESCRIPTION, description);
        values.put(PRODUCT_PRICE, price);
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    // Lấy danh sách tất cả sản phẩm
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndexOrThrow(PRODUCT_ID)));
                product.setName(cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_NAME)));
                product.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_DESCRIPTION)));
                product.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(PRODUCT_PRICE)));
                products.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return products;
    }

    // Thêm sản phẩm vào giỏ hàng
    public void addToCart(int productId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CART_PRODUCT_ID, productId);
        values.put(CART_QUANTITY, quantity);
        db.insert(TABLE_CART, null, values);
        db.close();
    }

    // Lấy giỏ hàng
    public List<CartItem> getCartItems() {
        List<CartItem> cartItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CART, null);
        if (cursor.moveToFirst()) {
            do {
                CartItem cartItem = new CartItem();
                cartItem.setCartId(cursor.getInt(cursor.getColumnIndexOrThrow(CART_ID)));
                cartItem.setProductId(cursor.getInt(cursor.getColumnIndexOrThrow(CART_PRODUCT_ID)));
                cartItem.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(CART_QUANTITY)));
                cartItems.add(cartItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cartItems;
    }

    // Phương thức xóa giỏ hàng
    public void clearCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, null, null);
        db.close();
    }

    // Phương thức để lấy sản phẩm theo ID
    public Product getProductById(int productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + PRODUCT_ID + "=?", new String[]{String.valueOf(productId)});
        if (cursor != null) {
            cursor.moveToFirst();
            Product product = new Product();
            product.setId(cursor.getInt(cursor.getColumnIndexOrThrow(PRODUCT_ID)));
            product.setName(cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_NAME)));
            product.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_DESCRIPTION)));
            product.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(PRODUCT_PRICE)));
            cursor.close();
            db.close();
            return product;
        } else {
            return null;
        }
    }

    // Lưu đánh giá
    public void addReview(int productId, String content, String date, String time, float rating, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(REVIEW_PRODUCT_ID, productId);
        values.put(REVIEW_CONTENT, content);
        values.put(REVIEW_DATE, date);  // Save date
        values.put(REVIEW_TIME, time);  // Save time
        values.put(REVIEW_RATING, rating);
        values.put(REVIEW_CATEGORY, category);
        db.insert(TABLE_REVIEWS, null, values);
        db.close();
    }


    public List<Review> getReviewsForProduct(int productId) {
        List<Review> reviews = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_REVIEWS + " WHERE " + REVIEW_PRODUCT_ID + "=?", new String[]{String.valueOf(productId)});

        if (cursor.moveToFirst()) {
            do {
                Review review = new Review();
                review.setContent(cursor.getString(cursor.getColumnIndexOrThrow(REVIEW_CONTENT)));
                review.setDate(cursor.getString(cursor.getColumnIndexOrThrow(REVIEW_DATE)));
                review.setTime(cursor.getString(cursor.getColumnIndexOrThrow(REVIEW_TIME)));
                review.setRating(cursor.getFloat(cursor.getColumnIndexOrThrow(REVIEW_RATING)));
                review.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(REVIEW_CATEGORY)));
                reviews.add(review);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return reviews;
    }


}
