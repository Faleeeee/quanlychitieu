package com.example.quanlychitieuapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DatabaseHelper {
    private Context context;
    private String DB_PATH_SUFFIX = "/databases/";
    private String DATABASE_NAME = "quanlychitieu.db";
    private SQLiteDatabase database;

    int idWalletChose = 1;

    public DatabaseHelper(Context context) {
        this.context = context;
        xuLySaoChepCSDL();
    }

    private void xuLySaoChepCSDL() {
        File dbFile = context.getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists()) {
            copyDatabase();
        }
//        else {
//            context.deleteDatabase(DATABASE_NAME);
//            copyDatabase();
//        }

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

        // Chuyển đổi định dạng ngày từ "dd/MM/yyyy" sang "yyyy-MM-dd"
        SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat sdfDatabase = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date dateFormatted = sdfInput.parse(day);
            day = sdfDatabase.format(dateFormatted);
        } catch (ParseException e) {
            e.printStackTrace();
        }

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

    public void xoaGiaoDich(int id_giaodich) {
        // Mở cơ sở dữ liệu
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

        // Xóa giao dịch theo id
        int rowsDeleted = database.delete("giaodich", "id=?", new String[]{String.valueOf(id_giaodich)});

        if (rowsDeleted > 0) {
            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
//            showAll();
        } else {
            Toast.makeText(context, "Không tìm thấy giao dịch để xóa", Toast.LENGTH_SHORT).show();
        }
    }

    public void editGiaoDich(int id_giaodich, int id_wal, Double money, String tenGiaoDich, String nhomGiaoDich, String day, String note) {
        // Mở cơ sở dữ liệu
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

        ContentValues row = new ContentValues();
        row.put("id_wal", id_wal);
        row.put("money", money);
        row.put("loai_giaodich", tenGiaoDich);
        row.put("group_name", nhomGiaoDich);
        row.put("day", day);
        row.put("note", note);

        // Sửa giao dịch theo id
        int rowsEdit = database.update("giaodich", row, "id=?", new String[]{String.valueOf(id_giaodich)});
        Toast.makeText(context, "Sửa giao dịch thành công", Toast.LENGTH_LONG).show();

    }

    public void showAll(List<String> listDataHeader, HashMap<String, List<GiaoDich>> listDataChild, int idWalletChose) {
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        Cursor cursor = database.query("giaodich", null, "id_wal = ?", new String[]{String.valueOf(idWalletChose)}, null, null, "day ASC");

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int id_wal = cursor.getInt(1);
            int money = cursor.getInt(2);
            String loai_giaodich = cursor.getString(3);
            String group_name = cursor.getString(4);
            String date = cursor.getString(5);
            String note = cursor.getString(6);

            GiaoDich giaoDich = new GiaoDich(id, id_wal, money, loai_giaodich, group_name, date, note);

            if (!listDataHeader.contains(date)) {
                listDataHeader.add(date);
                listDataChild.put(date, new ArrayList<GiaoDich>());
            }
            listDataChild.get(date).add(giaoDich);
        }
        cursor.close();
    }

    public void showAllTransactionsForWeek(List<String> listDataHeader, HashMap<String, List<GiaoDich>> listDataChild, int weekNumber, int idWalletChose) {
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

        // Xác định ngày đầu tiên và ngày cuối cùng của tuần
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.WEEK_OF_YEAR, weekNumber);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date startDate = calendar.getTime();

        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date endDate = calendar.getTime();

        // Chuyển đổi startDate và endDate sang định dạng "yyyy-MM-dd" để truy vấn cơ sở dữ liệu
        SimpleDateFormat sdfDatabase = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String startDateDatabase = sdfDatabase.format(startDate);
        String endDateDatabase = sdfDatabase.format(endDate);

        // Truy vấn cơ sở dữ liệu để lấy các giao dịch trong khoảng thời gian xác định và theo ví đã chọn
        Cursor cursor = database.query(
                "giaodich",
                null,
                "id_wal = ? AND date(day) BETWEEN date(?) AND date(?)",
                new String[]{String.valueOf(idWalletChose), startDateDatabase, endDateDatabase},
                null,
                null,
                "day DESC"
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int id_wal = cursor.getInt(1);
            int money = cursor.getInt(2);
            String loai_giaodich = cursor.getString(3);
            String group_name = cursor.getString(4);
            String date = cursor.getString(5);
            String note = cursor.getString(6);

            GiaoDich giaoDich = new GiaoDich(id, id_wal, money, loai_giaodich, group_name, date, note);

            // Chuyển đổi lại định dạng ngày từ "yyyy-MM-dd" sang "dd/MM/yyyy" để hiển thị
            SimpleDateFormat sdfDisplay = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            try {
                Date dateFormatted = sdfDatabase.parse(date);
                date = sdfDisplay.format(dateFormatted);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (!listDataHeader.contains(date)) {
                listDataHeader.add(date);
                listDataChild.put(date, new ArrayList<GiaoDich>());
            }
            listDataChild.get(date).add(giaoDich);
        }
        cursor.close();
    }


    private void listenClick(ListView lv, ArrayList<GiaoDich> data) {
        lv.setOnItemClickListener((parent, view, position, id) -> {
            GiaoDich selectedItem = data.get(position);
            Toast.makeText(context, "Bạn đã chọn: " + selectedItem.toString(), Toast.LENGTH_SHORT).show();

            Intent myIntent = new Intent(context, chitietchitieu.class);
            Bundle myBundle = new Bundle();
            myBundle.putInt("id_giaodich", selectedItem.getId());  // Thêm id của giao dịch vào bundle
            myBundle.putInt("id_wallet", selectedItem.getIdWallet());
            myBundle.putString("name", selectedItem.group_name);
            myBundle.putInt("money", selectedItem.money);
            myBundle.putString("loai_giaodich", selectedItem.loai_giaoDich);
            myBundle.putString("date", selectedItem.date);
            myBundle.putString("note", selectedItem.note);

            myIntent.putExtra("myPackageChiTietChiTieu", myBundle);
            context.startActivity(myIntent);
        });
    }


}
