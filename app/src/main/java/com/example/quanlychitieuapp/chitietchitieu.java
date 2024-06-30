package com.example.quanlychitieuapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.quanlychitieuapp.Fragment.thongke;

public class chitietchitieu extends AppCompatActivity {
    TextView tvName, tvMoney, tvDate, tvWallet;
    String name;
    int money;
    String date;
    String wallet;
    String note;
    String loaiGD;
    private int idGiaodich;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietchitieu);

        // Khởi tạo DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbarVi);
        setSupportActionBar(toolbar);

        tvName = findViewById(R.id.name);
        tvMoney = findViewById(R.id.money);
        tvDate = findViewById(R.id.date);
        tvWallet = findViewById(R.id.wallet);

        setContentText();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tool_bar_vi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.edit) {
            Bundle myBundle = new Bundle();
            myBundle.putInt("id_giaodich", idGiaodich);
            myBundle.putString("name", name);
            myBundle.putInt("money", money);
            myBundle.putString("date", date);
            myBundle.putString("wallet", wallet);
            myBundle.putString("note", note);
            // Add selectedGD if it exists
            myBundle.putString("loai_giaodich", loaiGD);

            Intent intent = new Intent(chitietchitieu.this, editGiaoDich.class);
            intent.putExtra("myPackageEditChiTieu", myBundle);
            startActivity(intent);
        } else if (itemId == R.id.delete) {
            AlertDialog.Builder myDialog = new AlertDialog.Builder(chitietchitieu.this);
            myDialog.setTitle("Question");
            myDialog.setMessage("Bạn có chắc chắn muốn xóa giao dịch này không?");

            myDialog.setPositiveButton("Yes", (dialog, which) -> {
                dbHelper.xoaGiaoDich(idGiaodich);
                Intent intent = new Intent(chitietchitieu.this, MainActivity.class);
                startActivity(intent);
                finish();
            });

            myDialog.setNegativeButton("No", (dialog, which) -> dialog.cancel());

            myDialog.create().show();
        }

        return super.onOptionsItemSelected(item);
    }


    private void getData() {
        Intent myIntent = getIntent();
        Bundle myBundle = myIntent.getBundleExtra("myPackageChiTietChiTieu");
        if (myBundle != null) {
            idGiaodich = myBundle.getInt("id_giaodich");
            name = myBundle.getString("name");
            money = myBundle.getInt("money");
            loaiGD = myBundle.getString("loai_giaodich");
            date = myBundle.getString("date");
            wallet = myBundle.getString("wallet");
            note = myBundle.getString("note");
            Toast.makeText(this, "ID giao dịch: " + idGiaodich, Toast.LENGTH_SHORT).show();
        }
    }

    private void setContentText() {
        getData();
        tvName.setText(name);
        tvMoney.setText(String.valueOf(money));
        tvDate.setText(date);
        tvWallet.setText(wallet);

        // Chuyển sang activity thống kê
//        Intent intent = new Intent(chitietchitieu.this, thongke.class);
//        startActivity(intent);
    }
}