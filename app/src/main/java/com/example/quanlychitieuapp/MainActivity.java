package com.example.quanlychitieuapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.quanlychitieuapp.R;

public class MainActivity extends AppCompatActivity {

    // Định nghĩa các đối tượng cho EditText và Button
    EditText edittext;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lấy đối tượng của EditText và Button
        button = findViewById(R.id.button);
        edittext = findViewById(R.id.editText);

        // Gắn sự kiện click vào Button để khởi tạo Intent
        button.setOnClickListener(arg -> {
            // Lấy số điện thoại từ EditText và chuyển nó thành String
            String phone_number = edittext.getText().toString();

            // Kiểm tra quyền thực hiện cuộc gọi
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // Yêu cầu cấp quyền thực hiện cuộc gọi
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return;
            }

            // Khởi tạo Intent với hành động ACTION_CALL
            Intent phone_intent = new Intent(Intent.ACTION_CALL);

            // Thiết lập dữ liệu cho Intent bằng cách phân tích số điện thoại
            phone_intent.setData(Uri.parse("tel:" + phone_number));

            // Khởi chạy Intent
            startActivity(phone_intent);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền được cấp, thực hiện cuộc gọi
                String phone_number = edittext.getText().toString();
                Intent phone_intent = new Intent(Intent.ACTION_CALL);
                phone_intent.setData(Uri.parse("tel:" + phone_number));
                startActivity(phone_intent);
            } else {
                // Quyền bị từ chối, hiển thị thông báo hoặc thực hiện các hành động khác
            }
        }
    }
}