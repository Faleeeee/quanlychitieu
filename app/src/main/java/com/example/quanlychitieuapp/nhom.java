package com.example.quanlychitieuapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class nhom extends AppCompatActivity {
    TabHost tabHost;
    ListView lvNhomChi;
    ListView lvNhomThu;

    ArrayAdapter<String> myadapterNhomChi;

    ArrayAdapter<String> myadapterNhomThu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhom);
        getSupportActionBar().setTitle("Loại giao dịch");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lvNhomChi = findViewById(R.id.lvNhomChi);
        lvNhomThu = findViewById(R.id.lvNhomThu);

        addControls();
    }

    private void addControls() {

        addListview();
        // Xử lý tabhost
        tabHost = findViewById(R.id.mytab);
        tabHost.setup();

        // Khai báo các tab con
        TabHost.TabSpec spec1, spec2;

        // tab1
        spec1 = tabHost.newTabSpec("Tab 1"); // tạo tab mới
        spec1.setIndicator("Chi"); // thiết lập chỉ báo cho tab1
        spec1.setContent(R.id.tabNhomChi); // tham chiếu cho tab1
        tabHost.addTab(spec1); // Thêm tab1 vào tab chính

        // tab2
        spec2 = tabHost.newTabSpec("Tab 2");
        spec2.setIndicator("Thu"); // thiết lập chỉ báo cho tab2
        spec2.setContent(R.id.tabNhomThu);
        tabHost.addTab(spec2);

        TabWidget tabWidget = tabHost.getTabWidget();
        for (int i = 0; i < tabWidget.getChildCount(); i++) {
            TextView tv = (TextView) tabWidget.getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(getResources().getColorStateList(R.color.tab_text_color));
        }
    }

    private void addListview() {
        //Xử lý ListView Nhom chi
        ArrayList<String> nhomChi;
        nhomChi = new ArrayList<>();
        nhomChi.add("Ăn uống");
        nhomChi.add("Bảo hiểm");
        nhomChi.add("Đầu tư");
        nhomChi.add("Di chuyển");
        nhomChi.add("Các chi phí khác");
        myadapterNhomChi = new ArrayAdapter<>(nhom.this, R.layout.list_item, nhomChi);
        lvNhomChi.setAdapter(myadapterNhomChi);

        listenClick(lvNhomChi, nhomChi, "Chi");

        //Xử lý ListView Nhom thu
        ArrayList<String> nhomThu;
        nhomThu = new ArrayList<>();
        nhomThu.add("Lương");
        nhomThu.add("Thu nhập khác");
        nhomThu.add("Tiền chuyển đến");
        nhomThu.add("Di chuyển");
        myadapterNhomThu = new ArrayAdapter<>(nhom.this, R.layout.list_item, nhomThu);
        lvNhomThu.setAdapter(myadapterNhomThu);
        listenClick(lvNhomThu, nhomThu, "Thu");

    }

    private void listenClick(ListView lv, ArrayList<String> data, String nhomChiTieu) {
        // Xử lý sự kiện khi click vào một item trong ListView
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                // Lấy giá trị của item được click
                String selectedItem = data.get(position);
                Toast.makeText(nhom.this, "Bạn đã chọn: " + selectedItem, Toast.LENGTH_SHORT).show();

                //khai báo intent
                Intent myIntent = new Intent(nhom.this, MainActivity.class);
                //đóng gói dữ liệu vào Bundle
                Bundle myBundle = new Bundle();
                //Đưa dữ liệu vào Bundle
                myBundle.putString("nhomChiTieu", nhomChiTieu);
                myBundle.putString("loaiChiTieu", selectedItem);
                //đưa bundle vào intent
                myIntent.putExtra("myPackageNhom", myBundle);
                startActivity(myIntent);
            }
        });

    }
}
