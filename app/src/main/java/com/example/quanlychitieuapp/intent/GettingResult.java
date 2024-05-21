package com.example.quanlychitieuapp.intent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychitieuapp.R;

public class GettingResult extends AppCompatActivity implements View.OnClickListener { // Thêm implements View.OnClickListener
    private EditText name_input;
    private EditText pass;
    private EditText mssv;
    private Button btn;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_result);

        name_input = findViewById(R.id.name_input);
        pass = findViewById(R.id.pass);
        mssv = findViewById(R.id.mssv);
        btn = findViewById(R.id.btn_submit);

        btn.setOnClickListener(this); // Đúng cách sử dụng this
    }

    @Override // Thêm @Override để ghi đè phương thức onClick
    public void onClick(View view) {
        if (view == btn) {
            if (name_input.getText().toString().isEmpty() || pass.getText().toString().isEmpty() || mssv.getText().toString().isEmpty()) {
                Toast.makeText(this, "Vui long kiem tra lai", Toast.LENGTH_SHORT).show();
            } else if (name_input.getText().toString().equals("nhom5") && pass.getText().toString().equals("12345") && mssv.getText().toString().equals("6789")) {
                // Khai báo intent
                Intent myIntent = new Intent(GettingResult.this, Result.class);
                // Lấy dữ liệu
                String name = name_input.getText().toString();
                String password = pass.getText().toString();
                String masv = mssv.getText().toString();
                // Đóng gói dữ liệu vào Bundle
                Bundle myBundle = new Bundle();
                // Đưa dữ liệu vào Bundle
                myBundle.putString("name", name);
                myBundle.putString("pass", password);
                myBundle.putString("mssv", masv);
                // Đưa bundle vào intent
                myIntent.putExtra("myPackage", myBundle);
                startActivity(myIntent);
            } else {
                Toast.makeText(this, "Nhap sai thong tin!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
