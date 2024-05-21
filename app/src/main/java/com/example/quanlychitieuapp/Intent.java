package com.example.quanlychitieuapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieuapp.intent.Explicit;
import com.example.quanlychitieuapp.intent.GettingResult;
import com.example.quanlychitieuapp.intent.Implicit;

public class Intent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intent);
        Button btn3_6_1 = (Button) findViewById(R.id.btn3_6_1);
        btn3_6_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.content.Intent intent3_6_1 = new android.content.Intent(Intent.this, Explicit.class);
                startActivity(intent3_6_1);
            }
        });

        Button btn3_6_2 = (Button) findViewById(R.id.btn3_6_2);
        btn3_6_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.content.Intent intent3_6_2 = new android.content.Intent(Intent.this, Implicit.class);
                startActivity(intent3_6_2);
            }
        });

        Button btn3_6_3 = (Button) findViewById(R.id.btn3_6_3);
        btn3_6_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.content.Intent intent3_6_3 = new android.content.Intent(Intent.this, GettingResult.class);
                startActivity(intent3_6_3);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}