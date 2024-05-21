package com.example.quanlychitieuapp.intent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieuapp.R;

public class Result extends AppCompatActivity implements View.OnClickListener { // Thêm implements View.OnClickListener

    Button btnback;
    TextView ketQua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ketQua = findViewById(R.id.result);
        btnback = findViewById(R.id.buttonBack);
        btnback.setOnClickListener(this); // Đúng cách sử dụng this

        // Nhận intent
        Intent myIntent = getIntent();
        // Lấy bundle khỏi intent
        Bundle myBundle = myIntent.getBundleExtra("myPackage");
        // Lấy dữ liệu khỏi bundle
        String name = myBundle.getString("name");
        String result = "Xin chao " + name;
        ketQua.setText(result);
    }

    @Override
    public void onClick(View view) { // Thêm @Override và triển khai phương thức onClick
        if (view == btnback) {
            finish();
        }
    }
}
