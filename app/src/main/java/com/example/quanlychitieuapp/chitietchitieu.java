package com.example.quanlychitieuapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class chitietchitieu extends AppCompatActivity {
    TextView tvName, tvMoney, tvDate, tvWallet;
    String name;
    int money;
    String date;
    String wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietchitieu);

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

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.edit) {
            Toast.makeText(chitietchitieu.this, "Bạn đã chọn: Edit", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.delete) {
            Toast.makeText(chitietchitieu.this, "Bạn đã chọn: Delete", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }


    private void getData() {
        Intent myIntent = getIntent();
        Bundle myBundle = myIntent.getBundleExtra("myPackageChiTietChiTieu");
        if (myBundle != null) {
            name = myBundle.getString("name");
            money = myBundle.getInt("money");
            date = myBundle.getString("date");
            wallet = myBundle.getString("wallet");
        }
    }

    private void setContentText() {
        getData();
        tvName.setText(name);
        tvMoney.setText(String.valueOf(money));
        tvDate.setText(date);
        tvWallet.setText(wallet);
    }
}
