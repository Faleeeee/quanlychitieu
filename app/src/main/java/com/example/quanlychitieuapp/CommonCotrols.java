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

import com.example.quanlychitieuapp.CommonControls.Checkbox;
import com.example.quanlychitieuapp.CommonControls.EditT;
import com.example.quanlychitieuapp.CommonControls.Imageview;
import com.example.quanlychitieuapp.CommonControls.Radiobutton;
import com.example.quanlychitieuapp.CommonControls.Scrollview;
import com.example.quanlychitieuapp.CommonControls.TextView;
import com.example.quanlychitieuapp.CommonControls.button;

public class CommonCotrols extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_common_cotrols);

        Button btn3_2_1 = (Button) findViewById(R.id.btn3_2_1);
        btn3_2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentControls1 = new Intent(CommonCotrols.this, TextView.class);
                startActivity(intentControls1);

            }
        });

        Button btn3_2_2 = (Button) findViewById(R.id.btn3_2_2);
        btn3_2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentControls2 = new Intent(CommonCotrols.this, EditT.class);
                startActivity(intentControls2);

            }
        });

        Button btn3_2_3 = (Button) findViewById(R.id.btn3_2_3);
        btn3_2_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentControls3 = new Intent(CommonCotrols.this, button.class);
                startActivity(intentControls3);

            }
        });

        Button btn3_2_4 = (Button) findViewById(R.id.btn3_2_4);
        btn3_2_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentControls4 = new Intent(CommonCotrols.this, Checkbox.class);
                startActivity(intentControls4);

            }
        });

        Button btn3_2_5 = (Button) findViewById(R.id.btn3_2_5);
        btn3_2_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentControls5 = new Intent(CommonCotrols.this, Radiobutton.class);
                startActivity(intentControls5);

            }
        });

        Button btn3_2_6 = (Button) findViewById(R.id.btn3_2_6);
        btn3_2_6.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentControls6 = new Intent(CommonCotrols.this, Imageview.class);
                startActivity(intentControls6);

            }
        }));

        Button btn3_2_7 = (Button) findViewById(R.id.btn3_2_7);
        btn3_2_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentControls7 = new Intent(CommonCotrols.this, Scrollview.class);
                startActivity(intentControls7);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}