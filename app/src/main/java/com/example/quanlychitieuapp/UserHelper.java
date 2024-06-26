package com.example.quanlychitieuapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserHelper {
    private Context context;
    private SQLiteDatabase database;
    private static final String DATABASE_NAME = "quanlychitieu.db";

    public UserHelper(Context context) {
        this.context = context;
    }

    public boolean registerUser(String email, String password, String confirmPassword) {
        if (confirmPassword(password, confirmPassword)) {
            database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

            if (isEmailExists(email)) {
                return false;
            }

            ContentValues values = new ContentValues();
            values.put("email", email);
            values.put("password", Util.hashPassword(password));
            values.put("username", email);
            long result = database.insert("user", null, values);

            Log.d("UserHelper", "Register result: " + result);

            return result != -1;
        } else {
            return false;
        }
    }

    public boolean loginUser(String email, String password) {
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        try (Cursor cursor = database.rawQuery("SELECT * FROM user WHERE email = ?", new String[]{email})) {
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") String hashedPassword = cursor.getString(cursor.getColumnIndex("password"));
                String hashedInputPassword = Util.hashPassword(password);
                Log.d("UserHelper", "Stored Password: " + hashedPassword);
                Log.d("UserHelper", "Input Password (hashed): " + hashedInputPassword);
                return hashedPassword.equals(hashedInputPassword);
            }
        }
        return false;
    }

    public boolean changePassword(int userId, String oldPassword, String newPassword, String confirmPassword) {
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        String hashedOldPassword = Util.hashPassword(oldPassword);
        String hashedNewPassword = Util.hashPassword(newPassword);

        try (Cursor cursor = database.rawQuery("SELECT * FROM user WHERE id = ? AND password = ?", new String[]{String.valueOf(userId), hashedOldPassword})) {
            if (cursor.moveToFirst()) {
                ContentValues values = new ContentValues();
                values.put("password", hashedNewPassword);
                if (confirmPassword(newPassword, confirmPassword)) {
                    database.update("user", values, "id = ?", new String[]{String.valueOf(userId)});
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressLint("Range")
    public int getUserIdByEmail(String email) {
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        int userId = -1;
        try (Cursor cursor = database.rawQuery("SELECT id FROM user WHERE email = ?", new String[]{email})) {
            if (cursor.moveToFirst()) {
                userId = cursor.getInt(cursor.getColumnIndex("id"));
            }
        }
        return userId;
    }

    public boolean isEmailExists(String email) {
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        try (Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM user WHERE email = ?", new String[]{email})) {
            if (cursor.moveToFirst()) {
                return cursor.getInt(0) > 0;
            }
        }
        return false;
    }

    private boolean confirmPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    public boolean checkEmail(String email) {
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        Cursor cursor = database.query("user", new String[]{"email"}, "email = ?", new String[]{email}, null, null, null);
        boolean emailExists = (cursor.getCount() > 0);
        cursor.close();
        database.close();
        return emailExists;
    }

    public boolean deleteUser(int userId) {
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        int rowsDeleted = database.delete("user", "id = ?", new String[]{String.valueOf(userId)});
        return rowsDeleted > 0;
    }
}