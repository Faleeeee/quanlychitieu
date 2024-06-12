package com.example.quanlychitieuapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class nhom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nhom);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    public void thu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Giaodich", "Thu"); // Đóng gói chuỗi dữ liệu
        startActivity(intent);
    }

    public void chi(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Giaodich", "Chi"); // Đóng gói chuỗi dữ liệu
        startActivity(intent);
    }
}