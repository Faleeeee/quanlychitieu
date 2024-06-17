package com.example.quanlychitieuapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class thongke extends AppCompatActivity {
    String DB_PATH_SUFFIX = "/databases/";

    String DATABASE_NAME = "quanlychitieu.db";

    SQLiteDatabase database = null;

    ListView listView;
    ArrayList<String> arrThongKe;
    ArrayAdapter<String> adapterDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thongke);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        xuLySaoChepCSDL();
        addConTrols();
        addGiaoDich();
        showAll();
    }

    private void addGiaoDich() {
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        //nhận intent
        Intent myIntent = getIntent();
//lấy bundle khỏi intent
        Bundle myBundle = myIntent.getBundleExtra("myPackage");
        if (myBundle != null) {
//lấy dữ liệu khỏi bundle
            int id_wal = myBundle.getInt("id_wal");
            Double money = myBundle.getDouble("money");
            String giaoDich = myBundle.getString("giaoDich");
            String group_name = myBundle.getString("loaiGiaoDich");
            String day = myBundle.getString("day");
            String note = myBundle.getString("note");
            ContentValues row = new ContentValues();
            row.put("id_wal", id_wal);
            row.put("money", money);
            row.put("loai_giaodich", giaoDich);
            row.put("group_name", group_name);
            row.put("day", day);
            row.put("note", note);

            long r = database.insert("giaodich", null, row);
            Toast.makeText(this, "Them thanh cong", Toast.LENGTH_LONG).show();

            showAll();
        } else {
            Log.e("thongke", "Bundle is null");
        }
    }

    private void showAll() {
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        Cursor cursor = database.query("giaodich", null, null, null, null, null, null);
        arrThongKe.clear();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int id_wal = cursor.getInt(1);
            int money = cursor.getInt(2);
            String loaigiaoDich = cursor.getString(3);
            String group_name = cursor.getString(4);
            String day = cursor.getString(5);
            String note = cursor.getString(6);

            arrThongKe.add(id + "-" + id_wal + "-" + money + "-" + loaigiaoDich + "-" + group_name + "-" + day + "-" + note);
        }
        cursor.close();
        adapterDB.notifyDataSetChanged();
    }


    private void addConTrols() {
        listView = findViewById(R.id.listView);
        arrThongKe = new ArrayList<>();
        adapterDB = new ArrayAdapter<String>(this, R.layout.list_item, arrThongKe);
        listView.setAdapter(adapterDB);
    }

    private void xuLySaoChepCSDL() {
        File dbFile = getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists()) {
            copyDatabase();
        } else {
            dbFile.delete();  // Xóa tệp CSDL cũ nếu tồn tại
        }
        copyDatabase();
    }

    private void copyDatabase() {
        try {
            InputStream myInput = getAssets().open(DATABASE_NAME);
            String outFileName = getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
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
            Log.e("Loi r", e.toString());
        }
    }


}