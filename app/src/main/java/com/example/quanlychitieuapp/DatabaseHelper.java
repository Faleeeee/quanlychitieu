package com.example.quanlychitieuapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.regex.Pattern;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "users.db";
    private static final String TABLE_NAME = "users";
    private static final String COL1 = "ID";
    private static final String COL2 = "Email";
    private static final String COL3 = "Password";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );
    private Context context;

    public DatabaseHelper(@Nullable Context context) {

        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT, " +
                COL3 + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean registerUser(String email, String password, String confirmPassword) {
        if (confirmPassword(password, confirmPassword)) {


            if (isEmailExists(email)) {
                return false;
            }

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COL2, email);
            values.put(COL3, password);
            long result = db.insert(TABLE_NAME, null, values);
            return result != -1;
        } else {
            return false;
        }
    }


    private boolean confirmPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COL2}, COL2 + "=?", new String[]{email}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL2, COL3}; // Lấy cả email và password
        String selection = COL2 + "=?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String dbPassword = cursor.getString(cursor.getColumnIndex(COL3));
            return dbPassword.equals(password); // So sánh password nhập vào với password trong database
        }
        cursor.close();
        return false;
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("users", new String[]{"email"}, "email = ?", new String[]{email}, null, null, null);
        boolean emailExists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return emailExists;

    }
    @SuppressLint("Range")
    public int getUserIdByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL1}; // Lấy cột ID
        String selection = COL2 + "=?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int userId = -1;
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex(COL1));
        }
        cursor.close();
        return userId;
    }

    public boolean changePassword(int userId, String oldPassword, String newPassword, String confirmPassword) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Kiểm tra mật khẩu cũ
        Cursor cursor = db.query(TABLE_NAME, new String[]{COL3}, COL1 + "=?",
                new String[]{String.valueOf(userId)}, null, null, null);
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String dbPassword = cursor.getString(cursor.getColumnIndex(COL3));
            cursor.close();

            // Kiểm tra xem mật khẩu cũ có chính xác không
            if (dbPassword.equals(oldPassword)) {
                // Kiểm tra xem mật khẩu mới và xác nhận mật khẩu mới có khớp nhau không
                if (newPassword.equals(confirmPassword)) {
                    // Cập nhật mật khẩu mới vào database
                    ContentValues values = new ContentValues();
                    values.put(COL3, newPassword);
                    int rowsAffected = db.update(TABLE_NAME, values, COL1 + "=?",
                            new String[]{String.valueOf(userId)});
                    db.close();
                    return rowsAffected > 0;
                } else {
                    db.close();
                    return false; // Mật khẩu mới và xác nhận mật khẩu không khớp
                }
            } else {
                db.close();
                return false; // Mật khẩu cũ không đúng
            }
        } else {
            cursor.close();
            db.close();
            return false; // Không tìm thấy người dùng với ID này
        }
    }

}
