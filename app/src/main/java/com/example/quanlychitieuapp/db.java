package com.example.quanlychitieuapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class db {
    private Context context;
    private String DB_PATH_SUFFIX = "/databases/";
    private String DATABASE_NAME = "quanlychitieu.db";
    private SQLiteDatabase database;

    public db(Context context) {
        this.context = context;
        xuLySaoChepCSDL();
    }

    private void xuLySaoChepCSDL() {
        File dbFile = context.getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists()) {
            copyDatabase();
        }
//        else {
//            dbFile.delete();  // Xóa tệp CSDL cũ nếu tồn tại
//        }
//        copyDatabase();
    }

    private void copyDatabase() {
        try {
            InputStream myInput = context.getAssets().open(DATABASE_NAME);
            String outFileName = context.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
            File f = new File(context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists()) {
                f.mkdir();
            }

            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, len);
            }
            myOutput.flush();
            myInput.close();
            myOutput.close();

        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }

    public void addGiaoDich(int id_wal, Double money, String tenGiaoDich, String nhomGiaoDich, String day, String note) {
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);


        ContentValues row = new ContentValues();
        row.put("id_wal", id_wal);
        row.put("money", money);
        row.put("loai_giaodich", tenGiaoDich);
        row.put("group_name", nhomGiaoDich);
        row.put("day", day);
        row.put("note", note);

        long r = database.insert("giaodich", null, row);
        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_LONG).show();
    }


    public ArrayList<String> showAll() {
        ArrayList<String> arrThongKe = new ArrayList<>();
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        Cursor cursor = database.query("giaodich", null, null, null, null, null, null);

        Map<Integer, List<GiaoDich>> giaoDichMap = new HashMap<>();
        int tongTienThu = 0;
        int tongTienChi = 0;

        while (cursor.moveToNext()) {
            int id_wal = cursor.getInt(1);
            int money = cursor.getInt(2);
            String loai_giaoDich = cursor.getString(3);
            String group_name = cursor.getString(4);
            String note = cursor.getString(6);
            GiaoDich giaoDich = new GiaoDich(id_wal, money, loai_giaoDich, group_name);

            if (!giaoDichMap.containsKey(id_wal)) {
                giaoDichMap.put(id_wal, new ArrayList<GiaoDich>());
            }
            arrThongKe.add(group_name + "   " + money + "   " + note);
            giaoDichMap.get(id_wal).add(giaoDich);

            // Cập nhật tổng tiền thu và chi
            if (giaoDich.loai_giaoDich.equals("thu")) {
                tongTienThu += money;
            } else if (giaoDich.loai_giaoDich.equals("chi")) {
                tongTienChi += money;
            }
        }
        cursor.close();

        // Lưu tổng tiền thu và chi vào Bundle và trả về Activity
        Bundle resultBundle = new Bundle();
        resultBundle.putInt("tongTienThu", tongTienThu);
        resultBundle.putInt("tongTienChi", tongTienChi);

        // Đóng gói arrThongKe và resultBundle vào kết quả trả về
        ArrayList<String> resultList = new ArrayList<>();
        resultList.add(String.valueOf(tongTienThu));
        resultList.add(String.valueOf(tongTienChi));
        resultList.addAll(arrThongKe);

        return resultList;
    }

    // Lớp GiaoDich để lưu trữ thông tin giao dịch
    private class GiaoDich {
        int id_wal;
        int money;
        String loai_giaoDich;
        String group_name;

        public GiaoDich(int id_wal, int money, String loai_giaoDich, String group_name) {
            this.id_wal = id_wal;
            this.money = money;
            this.loai_giaoDich = loai_giaoDich;
            this.group_name = group_name;
        }
    }

    public long registerUser(String email, String password) {
        // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        String hashedPassword = hashPassword(password);

        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("password", hashedPassword);

        long newId = database.insert("user", null, values);
        return newId;
    }

    // Phương thức kiểm tra email đã tồn tại hay chưa
    public boolean isEmailExists(String email) {
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        try (Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM users WHERE email = ?", new String[]{email})) {
            if (cursor.moveToFirst()) {
                return cursor.getInt(0) > 0;
            }
        }
        return false;
    }

    // Phương thức đăng nhập
    public boolean loginUser(String email, String password) {
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        try (Cursor cursor = database.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email})) {
            if (cursor.moveToFirst()) {
                // So sánh mật khẩu đã mã hóa trong cơ sở dữ liệu với mật khẩu đã nhập
                @SuppressLint("Range") String hashedPassword = cursor.getString(cursor.getColumnIndex("password"));
                return hashedPassword.equals(hashPassword(password));
            }
        }
        return false;
    }

    // Phương thức đổi mật khẩu
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        String hashedOldPassword = hashPassword(oldPassword);
        String hashedNewPassword = hashPassword(newPassword);

        // Kiểm tra xem email và mật khẩu cũ có chính xác không
        try (Cursor cursor = database.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", new String[]{email, hashedOldPassword})) {
            if (cursor.moveToFirst()) {
                // Cập nhật mật khẩu mới
                database.execSQL("UPDATE users SET password = ? WHERE email = ?", new String[]{hashedNewPassword, email});
                return true;
            }
        }
        return false;
    }

    // Phương thức lấy user_id dựa trên email
    @SuppressLint("Range")
    public int getUserIdByEmail(String email) {
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        int userId = -1;
        try (Cursor cursor = database.rawQuery("SELECT id FROM users WHERE email = ?", new String[]{email})) {
            if (cursor.moveToFirst()) {
                userId = cursor.getInt(cursor.getColumnIndex("id"));
            }
        }
        return userId;
    }

    // Hàm băm mật khẩu bằng SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | IOException e) {
            Log.e("DatabaseHelper", "Lỗi mã hóa mật khẩu: " + e.getMessage());
            return null;
        }
    }
}