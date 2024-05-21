package com.example.quanlychitieuapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieuapp.XML.CommonLayout;
import com.example.quanlychitieuapp.XML.ToastAndAlertDialog;
import com.example.quanlychitieuapp.XML.ViewGroup;

public class xml extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_xml);
        Button btn3_1 = (Button) findViewById(R.id.btn3_1);
        btn3_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentView = new Intent(xml.this, ViewGroup.class);
                startActivity(intentView);

            }
        });

        Button btn3_2 = (Button) findViewById(R.id.btn3_2);
        btn3_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentView2 = new Intent(xml.this, CommonLayout.class);
                startActivity(intentView2);

            }
        });

        Button btn3_3 = (Button) findViewById(R.id.btn3_3);
        btn3_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentView3 = new Intent(xml.this, ToastAndAlertDialog.class);
                startActivity(intentView3);

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}