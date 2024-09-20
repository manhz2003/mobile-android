package com.eaut20210719.thuchanh.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.eaut20210719.thuchanh.models.Student;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "students.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_STUDENTS = "students";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_STUDENT_ID = "student_id";
    private static final String COLUMN_FULL_NAME = "full_name";
    private static final String COLUMN_MATH = "math";
    private static final String COLUMN_PHYSICS = "physics";
    private static final String COLUMN_CHEMISTRY = "chemistry";
    private static final String COLUMN_LITERATURE = "literature";
    private static final String COLUMN_ENGLISH = "english";
    private static final String COLUMN_AVERAGE = "average";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_STUDENTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_STUDENT_ID + " TEXT, " +
                COLUMN_FULL_NAME + " TEXT, " +
                COLUMN_MATH + " REAL, " +
                COLUMN_PHYSICS + " REAL, " +
                COLUMN_CHEMISTRY + " REAL, " +
                COLUMN_LITERATURE + " REAL, " +
                COLUMN_ENGLISH + " REAL, " +
                COLUMN_AVERAGE + " REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    public long addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_ID, student.getStudentId());
        values.put(COLUMN_FULL_NAME, student.getFullName());
        values.put(COLUMN_MATH, student.getMath());
        values.put(COLUMN_PHYSICS, student.getPhysics());
        values.put(COLUMN_CHEMISTRY, student.getChemistry());
        values.put(COLUMN_LITERATURE, student.getLiterature());
        values.put(COLUMN_ENGLISH, student.getEnglish());
        values.put(COLUMN_AVERAGE, student.getAverage());

        return db.insert(TABLE_STUDENTS, null, values);
    }
}
