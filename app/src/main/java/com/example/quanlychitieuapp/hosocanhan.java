package com.example.quanlychitieuapp;

import android.annotation.SuppressLint;
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

public class hosocanhan extends AppCompatActivity {

    private TextView emailTextView;
    private Button btn6,btn7;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosocanhan);

        emailTextView = findViewById(R.id.emailTextView);
        btn6=findViewById(R.id.btn6);
        btn7=findViewById(R.id.btn7);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("email")) {
            String email = intent.getStringExtra("email"); // Sửa dòng này
            emailTextView.setText("Email: " + email);
        }
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openThayDoiMatKhauActivity();
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDangnhapActivity();
            }
        });
    }


    // Hàm mở Activity thay đổi mật khẩu
    private void openThayDoiMatKhauActivity() {
        Intent intent = new Intent(this, doimatkhau.class);
        startActivity(intent);
    }
    private void openDangnhapActivity(){
        Intent intent1=new Intent(this, dangnhap.class);
        startActivity(intent1);
    }
}