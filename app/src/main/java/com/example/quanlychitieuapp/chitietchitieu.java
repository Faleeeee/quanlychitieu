package com.example.quanlychitieuapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

        // Set title in ActionBar
        getSupportActionBar().setTitle("Chi tiết");

        // Initialize views
        tvName = findViewById(R.id.name);
        tvMoney = findViewById(R.id.money);
        tvDate = findViewById(R.id.date);
        tvWallet = findViewById(R.id.wallet);

        setContentText();
    }

    private void getData() {
        // Receive intent
        Intent myIntent = getIntent();
        // Get bundle from intent
        Bundle myBundle = myIntent.getBundleExtra("myPackageChiTietChiTieu");
        if (myBundle != null) {
            // Extract data from bundle
            name = myBundle.getString("name");
            money = myBundle.getInt("money");
            date = myBundle.getString("date");
            wallet = myBundle.getString("wallet");
        }
    }

    private void setContentText() {
        getData();
        tvName.setText(name);
        tvMoney.setText(String.valueOf(money)); // Convert int to String
        tvDate.setText(date);
        tvWallet.setText(wallet);
    }
}
