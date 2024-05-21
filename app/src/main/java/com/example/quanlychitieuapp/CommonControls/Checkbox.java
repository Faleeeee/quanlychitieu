package com.example.quanlychitieuapp.CommonControls;

import android.os.Bundle;

import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieuapp.R;

public class Checkbox extends AppCompatActivity implements View.OnClickListener {

    CheckBox android;
    CheckBox java;
    CheckBox unity;
    CheckBox php;
    CheckBox python;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_check_box);

        android = (CheckBox) findViewById(R.id.chckAndroid);
        android.setOnClickListener(this);

       java = (CheckBox) findViewById(R.id.chckJava);
       java.setOnClickListener(this);

       unity = (CheckBox) findViewById(R.id.chckUnity);
       unity.setOnClickListener(this);

       php = (CheckBox) findViewById(R.id.chckPHP);
       php.setOnClickListener(this);

       python = (CheckBox) findViewById(R.id.chckPython);
       python.setOnClickListener(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.chckAndroid){
            Toast.makeText(getApplicationContext(), "Android", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.chckPython) {
            Toast.makeText(getApplicationContext(), "Python", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.chckPHP) {
            Toast.makeText(getApplicationContext(), "PHP", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.chckUnity) {
            Toast.makeText(getApplicationContext(), "Unity", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.chckJava) {
            Toast.makeText(getApplicationContext(), "Java", Toast.LENGTH_SHORT).show();
        }

    }
}