package com.example.quanlychitieuapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class delete_account_confirmation extends AppCompatActivity {
    private Button btn_confirm_delete, btn_cancel_delete;
    private UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account_confirmation);

        userHelper = new UserHelper(this);

        btn_confirm_delete = findViewById(R.id.btn_confirm_delete);
        btn_confirm_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa tài khoản
                SharedPreferences prefs = getSharedPreferences("user_info", MODE_PRIVATE);
                int userId = prefs.getInt("user_id", -1);

                if (userId != -1) {
                    boolean success = userHelper.deleteUser(userId);
                    if (success) {
                        Toast.makeText(delete_account_confirmation.this, "Xóa tài khoản thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(delete_account_confirmation.this, dangnhap.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(delete_account_confirmation.this, "Xóa tài khoản thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(delete_account_confirmation.this, "Bạn chưa đăng nhập", Toast.LENGTH_SHORT).show();
                }
                finish(); // Đóng Activity này
            }
        });

        btn_cancel_delete = findViewById(R.id.btn_cancel_delete);
        btn_cancel_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Quay về Activity trước đó
            }
        });
    }
}