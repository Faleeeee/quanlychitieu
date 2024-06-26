package com.example.quanlychitieuapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper {
    private Context context;
    private String DB_PATH_SUFFIX = "/databases/";
    private String DATABASE_NAME = "quanlychitieu.db";
    private SQLiteDatabase database;

    public DatabaseHelper(Context context) {
        this.context = context;
        xuLySaoChepCSDL();
    }

    private void xuLySaoChepCSDL() {
        File dbFile = context.getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists()) {
            copyDatabase();
        }
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

    public ArrayList<String> showAll(ListView listView) {
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
            String date = cursor.getString(5);
            String note = cursor.getString(6);
            GiaoDich giaoDich = new GiaoDich(id_wal, money, loai_giaoDich, group_name, date, note);

            listenClick(listView, arrThongKe, group_name, money, date, "Tien mat");

            if (!giaoDichMap.containsKey(id_wal)) {
                giaoDichMap.put(id_wal, new ArrayList<GiaoDich>());
            }
            arrThongKe.add(group_name + "   " + money + "   " + note);
            giaoDichMap.get(id_wal).add(giaoDich);

            if (giaoDich.loai_giaoDich.equals("thu")) {
                tongTienThu += money;
            } else if (giaoDich.loai_giaoDich.equals("chi")) {
                tongTienChi += money;
            }
        }

        cursor.close();

        Bundle resultBundle = new Bundle();
        resultBundle.putInt("tongTienThu", tongTienThu);
        resultBundle.putInt("tongTienChi", tongTienChi);

        ArrayList<String> resultList = new ArrayList<>();
        resultList.add(String.valueOf(tongTienThu));
        resultList.add(String.valueOf(tongTienChi));
        resultList.addAll(arrThongKe);

        return resultList;
    }

    private void listenClick(ListView lv, ArrayList<String> data, String name, int money, String date, String wallet) {
        lv.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = data.get(position);
            Toast.makeText(context, "Bạn đã chọn: " + selectedItem, Toast.LENGTH_SHORT).show();

            Intent myIntent = new Intent(context, chitietchitieu.class);
            Bundle myBundle = new Bundle();
            myBundle.putString("name", name);
            myBundle.putInt("money", money);
            myBundle.putString("date", date);
            myBundle.putString("wallet", wallet);

            myIntent.putExtra("myPackageChiTietChiTieu", myBundle);
            context.startActivity(myIntent);
        });
    }
}
