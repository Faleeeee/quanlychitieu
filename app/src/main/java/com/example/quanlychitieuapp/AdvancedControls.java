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

import com.example.quanlychitieuapp.Advancedcontrols.AutoComplete;
import com.example.quanlychitieuapp.Advancedcontrols.Gridview;
import com.example.quanlychitieuapp.Advancedcontrols.Listview;
import com.example.quanlychitieuapp.Advancedcontrols.Slidingdrawer;
import com.example.quanlychitieuapp.Advancedcontrols.SpinNer;
import com.example.quanlychitieuapp.Advancedcontrols.TabSelector;
import com.example.quanlychitieuapp.Advancedcontrols.TimeSelection;
import com.example.quanlychitieuapp.Advancedcontrols.menu;
import com.example.quanlychitieuapp.CommonControls.TextView;

public class AdvancedControls extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_advanced_controls);

        Button btn3_3_1 = (Button) findViewById(R.id.btn3_3_1);
        btn3_3_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdvanced1 = new Intent(AdvancedControls.this, Listview.class);
                startActivity(intentAdvanced1);

            }
        });

        Button btn3_3_2 = (Button) findViewById(R.id.btn3_3_2);
        btn3_3_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdvaced2 = new Intent(AdvancedControls.this, SpinNer.class);
                startActivity(intentAdvaced2);

            }
        });


        Button btn3_3_3 = (Button) findViewById(R.id.btn3_3_3);
        btn3_3_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdvaced3 = new Intent(AdvancedControls.this, Slidingdrawer.class);
                startActivity(intentAdvaced3);

            }
        });

        Button btn3_3_4 = (Button) findViewById(R.id.btn3_3_4);
        btn3_3_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdvaced4 = new Intent(AdvancedControls.this, AutoComplete.class);
                startActivity(intentAdvaced4);

            }
        });

        Button btn3_3_5 = (Button) findViewById(R.id.btn3_3_5);
        btn3_3_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdvaced5 = new Intent(AdvancedControls.this, TextView.class);
                startActivity(intentAdvaced5);

            }
        });

        Button btn3_3_6 = (Button) findViewById(R.id.btn3_3_6);
        btn3_3_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdvaced6 = new Intent(AdvancedControls.this, Gridview.class);
                startActivity(intentAdvaced6);

            }
        });

        Button btn3_3_7 = (Button) findViewById(R.id.btn3_3_7);
        btn3_3_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdvaced7 = new Intent(AdvancedControls.this, TimeSelection.class);
                startActivity(intentAdvaced7);

            }
        });
        Button btn3_3_8 = (Button) findViewById(R.id.btn3_3_8);
//        btn3_3_8.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentAdvaced8 = new Intent(AdvancedControls.this, PictureGallery.class);
//                startActivity(intentAdvaced8);
//
//            }
//        });

        Button btn3_3_9 = (Button) findViewById(R.id.btn3_3_9);
        btn3_3_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdvaced9 = new Intent(AdvancedControls.this, TabSelector.class);
                startActivity(intentAdvaced9);

            }
        });

        Button btn3_3_10 = (Button) findViewById(R.id.btn3_3_10);
        btn3_3_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdvaced10 = new Intent(AdvancedControls.this, menu.class);
                startActivity(intentAdvaced10);

            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}