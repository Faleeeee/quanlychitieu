package com.example.quanlychitieuapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        xuLySaoChepCSDL();
        addConTrols();
        showAll();
    }

    private void showAll() {
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        Cursor cursor = database.query("user", null, null, null, null, null, null);
        arrThongKe.clear();

        while (cursor.moveToNext()) {
            int ma = cursor.getInt(0);
            String mail = cursor.getString(1);
            String pass = cursor.getString(2);
            arrThongKe.add(ma + "-" + mail + "-" + pass);
        }
        cursor.close();
        adapterDB.notifyDataSetChanged();
    }


    private void addConTrols() {
        listView = findViewById(R.id.listView);
        arrThongKe = new ArrayList<>();
        adapterDB = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrThongKe);
        listView.setAdapter(adapterDB);
    }

    private void xuLySaoChepCSDL() {
        File dbFile = getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists()) {
            copyDatabase();
        } else {
            dbFile.delete();
            copyDatabase();
        }
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