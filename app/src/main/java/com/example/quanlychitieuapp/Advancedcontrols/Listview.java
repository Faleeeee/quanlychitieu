package com.example.quanlychitieuapp.Advancedcontrols;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieuapp.R;

import java.util.ArrayList;

public class Listview extends AppCompatActivity {

    TextView txt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
//1. Khởi tạo dữ liệu cho mảng arr (còn gọi là data source)
        final String arr1[] = {"Thuan Phat", "Quy Hoang",
                "Tuan Phat"};
//2. Khai báo Adapter và gán Data source vào ArrayAdapter
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr1);
//3. Khai báo Listview và đưa Adapter vào ListView
        ListView lv1 = findViewById(R.id.lv1);
        lv1.setAdapter(adapter1);
//4. Viết sự kiện khi Click vào một dòng trên ListView

    }
}