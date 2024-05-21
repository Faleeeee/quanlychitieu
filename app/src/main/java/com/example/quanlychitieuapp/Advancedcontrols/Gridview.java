package com.example.quanlychitieuapp.Advancedcontrols;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.AdapterView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieuapp.R;

public class Gridview extends AppCompatActivity {

    int logo[] = {R.drawable.logo1, R.drawable.logo2, R.drawable.logo1, R.drawable.logo2, R.drawable.logo1, R.drawable.logo2, R.drawable.logo1, R.drawable.logo2};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gridview);

        GridView simpleGrid = (GridView) findViewById(R.id.simpleGrid);
        customAdapterGrid adapter = new customAdapterGrid(getApplicationContext(), logo);
        simpleGrid.setAdapter(adapter);

        simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent_grid = new Intent(Gridview.this, Gridview2.class);
                intent_grid.putExtra("image", logo[arg2]);
                startActivity(intent_grid);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}