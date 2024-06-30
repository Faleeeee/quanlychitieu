package com.example.quanlychitieuapp.Fragment;

import android.content.Context; // Thêm import
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import com.example.quanlychitieuapp.DatabaseHelper;
import com.example.quanlychitieuapp.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class thongke extends Fragment {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database; // Khai báo biến database

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_thongke, container, false);

        // Khởi tạo DatabaseHelper
        dbHelper = new DatabaseHelper(getActivity()); // Lấy context từ getActivity()

        // Khởi tạo database
        database = getActivity().openOrCreateDatabase("quanlychitieu.db", Context.MODE_PRIVATE, null); // Sử dụng Context.MODE_PRIVATE

        // Tạo biểu đồ tròn
        createPieChart(view);
        return view;
    }

    private void createPieChart(View view) {
        Map<String, Integer> thongKe = getThongKeChiTieu();

        PieChart pieChart = new PieChart(getActivity()); // Sử dụng getActivity()
        FrameLayout chartContainer = view.findViewById(R.id.chartContainerThongKe);
        chartContainer.addView(pieChart);

        List<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : thongKe.entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        // Thiết lập PieDataSet
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueFormatter(new PercentFormatter(pieChart));
        dataSet.setValueTextSize(12f);

        // Thiết lập PieData
        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Thống kê chi tiêu");
        pieChart.animateY(1000);
        pieChart.invalidate();
    }

    private Map<String, Integer> getThongKeChiTieu() {
        Map<String, Integer> thongKe = new HashMap<>();

        Cursor cursor = database.query("giaodich", null, "loai_giaodich = 'chi'", null, null, null, null);

        while (cursor.moveToNext()) {
            String groupName = cursor.getString(cursor.getColumnIndex("group_name"));
            double money = cursor.getDouble(cursor.getColumnIndex("money")); // Sử dụng getDouble()

            if (thongKe.containsKey(groupName)) {
                thongKe.put(groupName, thongKe.get(groupName) + (int) money); // Sửa thành int
            } else {
                thongKe.put(groupName, (int) money); // Sửa thành int
            }
        }
        cursor.close();

        return thongKe;
    }
}