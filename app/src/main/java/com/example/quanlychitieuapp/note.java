package com.example.quanlychitieuapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class note extends AppCompatActivity {
    EditText note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_note);
    }

    public void btnBack(View view) {
        startActivity(new Intent(note.this, MainActivity.class));
    }

    public void btnSave(View view) {
        // Khai báo intent
        Intent myIntent = new Intent(note.this, MainActivity.class);

        // Lấy dữ liệu
        String note_content = note.getText().toString();

        // Đóng gói dữ liệu vào Bundle
        Bundle myBundle = new Bundle();

        // Đưa dữ liệu vào Bundle
        myBundle.putString("note", note_content);

        // Đưa bundle vào intent
        myIntent.putExtra("myPackageNote", myBundle);

        // Start the new activity
        startActivity(myIntent);
    }
}