package com.example.quanlychitieuapp.Advancedcontrols;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieuapp.R;

import java.util.List;

public class SpinNer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
//1. Khởi tạo dữ liệu cho mảng arr (còn gọi là data source)
        final String arr1[] = {"Thuan Phat", "Quy Hoang",
                "Tuan Phat"};
//2. Khai báo Adapter và gán Data source vào ArrayAdapter
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr1);
//3. Khai báo Listview và đưa Adapter vào ListView
        Spinner lv1 = findViewById(R.id.spinner);
        lv1.setAdapter(adapter1);
//4. Viết sự kiện khi Click vào một dòng trên ListView

    }

}