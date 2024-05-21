package com.example.quanlychitieuapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class twobutton extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_twobutton);
        Button btnInline = findViewById(R.id.inline);

        btnInline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(twobutton.this, khacnhau.class));
            }
        });
    }

    public void handleClick(View view) {
        // Xử lý sự kiện nhấn nút
        startActivity(new Intent(twobutton.this, giongnhau.class));
    }
}