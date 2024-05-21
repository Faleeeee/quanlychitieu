package com.example.quanlychitieuapp.Advancedcontrols;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieuapp.R;

import java.util.ArrayList;

public class TabSelector extends AppCompatActivity {

EditText edta, edtb;
Button btnSum;

TabHost mytab;


ListView listView;
ArrayList<String> mylist;
ArrayAdapter<String> myadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tab_selector);

        addControlls();
        addEvent();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEvent() {
        btnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double a = Double.parseDouble(edta.getText().toString());
                double b = Double.parseDouble(edtb.getText().toString());
                double c = a + b;

                mylist.add(a + " + " + b + " = " + c); // Thêm dữ liệu vào cho mảng
                myadapter.notifyDataSetChanged(); // Cập nhật lại Adapter

            }
        });
    }

    private void addControlls() {
        edta = (EditText) findViewById(R.id.edt_a);
        edtb = (EditText) findViewById(R.id.edtb);
        btnSum = (Button) findViewById(R.id.btnSum);

        //Xử lý ListView
        listView = findViewById(R.id.listView_tab2);
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(TabSelector.this, android.R.layout.simple_list_item_1, mylist);
        listView.setAdapter(myadapter);

        //Xử lý tabhost
        mytab = (TabHost) findViewById(R.id.mytab);
        mytab.setup();
        //Khai báo các tab con
        TabHost.TabSpec spec1, spec2;

        //Ứng với mỗi tab con chúng ta thực hiện 4 công việc

        //tab1
        spec1 = mytab.newTabSpec("t1");//tạo tab mới
        spec1.setContent(R.id.tab1); //tham chiếu cho tab1
        spec1.setIndicator("", getResources().getDrawable(R.drawable.cong)); // Thiết lập icon cho tab
        mytab.addTab(spec1); // Thêm tab1 vào tab chính

        //tab2
        spec2 = mytab.newTabSpec("t2");
        spec2.setContent(R.id.tab2);
        spec2.setIndicator("", getResources().getDrawable(R.drawable.lichsu));
        mytab.addTab(spec2);
    }
}