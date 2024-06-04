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

import com.example.quanlychitieuapp.Fragment.caiDatFragment;

public class dangnhap extends AppCompatActivity {

    Button registerTextView;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dangnhap);
        registerTextView=findViewById(R.id.registerTextView);
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                Intent myintent2=new Intent(dangnhap.this, sign_up.class);
                startActivity(myintent2);
            }


        });

        loginButton=findViewById(R.id. loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent myintent3=new Intent(dangnhap.this, caiDatFragment.class);
                startActivity(myintent3);
            }
        });



    }
}