package com.example.quanlychitieuapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class thongke extends AppCompatActivity {
    String DB_PATH_SUFFIX = "/databases/";
    String DATABASE_NAME = "quanlychitieu.db";
    SQLiteDatabase database = null;
    TextView tienThu;
    TextView tienChi;
    TextView tongTien;
    ListView listView;
    ArrayList<String> arrThongKe;
    ArrayAdapter<String> adapterDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_thongke);  // setContentView trước

        tienChi = findViewById(R.id.tienRa);
        tienThu = findViewById(R.id.tienVao);
        tongTien = findViewById(R.id.tongTien);

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

        // Dùng HashMap để nhóm các giao dịch theo id_wal
        Map<Integer, List<GiaoDich>> giaoDichMap = new HashMap<>();

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
        }
        cursor.close();

        // Tính tổng tiền thu và chi cho mỗi id_wal và cập nhật danh sách hiển thị
        for (Map.Entry<Integer, List<GiaoDich>> entry : giaoDichMap.entrySet()) {
            int id_wal = entry.getKey();
            List<GiaoDich> giaoDichList = entry.getValue();

            int tongTienThu = 0;
            int tongTienChi = 0;

            for (GiaoDich giaoDich : giaoDichList) {
                if (giaoDich.loai_giaoDich.equals("thu")) {
                    tongTienThu += giaoDich.money;
                } else if (giaoDich.loai_giaoDich.equals("chi")) {
                    tongTienChi += giaoDich.money;
                }
            }
            tienThu.setText(String.valueOf(tongTienThu));  // Chuyển đổi giá trị số nguyên thành chuỗi
            tienChi.setText(String.valueOf(tongTienChi));  // Chuyển đổi giá trị số nguyên thành chuỗi
            tongTien.setText(String.valueOf(tongTienThu - tongTienChi));  // Chuyển đổi giá trị số nguyên thành chuỗi
        }
        adapterDB.notifyDataSetChanged();
    }

    private void addConTrols() {
        listView = findViewById(R.id.listView);
        arrThongKe = new ArrayList<>();
        adapterDB = new ArrayAdapter<>(this, R.layout.list_item, arrThongKe);
        listView.setAdapter(adapterDB);
    }

    private void xuLySaoChepCSDL() {
        File dbFile = getDatabasePath(DATABASE_NAME);

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
}
