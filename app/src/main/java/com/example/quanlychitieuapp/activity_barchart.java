package com.example.quanlychitieuapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activity_barchart extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchart);

        dbHelper = new DatabaseHelper(this);
        database = openOrCreateDatabase("quanlychitieu.db", Context.MODE_PRIVATE, null);

        createBarChart();
    }

    private void createBarChart() {
        Map<String, Integer> thongKe = getThongKeChiTieu();
        int tongTienChi = 0;

        BarChart barChart = new BarChart(this);
        FrameLayout chartContainer = findViewById(R.id.chartContainerBarChart);
        chartContainer.addView(barChart);

        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : thongKe.entrySet()) {
            entries.add(new BarEntry(entries.size(), entry.getValue()));
            labels.add(entry.getKey());
            tongTienChi += entry.getValue();
        }

        BarDataSet dataSet = new BarDataSet(entries, "Chi tiêu theo loại");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        BarData data = new BarData(dataSet);

        barChart.setData(data);
        barChart.getDescription().setEnabled(false);
        barChart.setFitBars(true);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

        barChart.invalidate();

        TextView tvTongTienChi = findViewById(R.id.tvTongTienChi);
        tvTongTienChi.setText("Tổng tiền chi: " + tongTienChi);
    }

    private Map<String, Integer> getThongKeChiTieu() {
        Map<String, Integer> thongKe = new HashMap<>();

        Cursor cursor = database.query("giaodich", null, "loai_giaodich = 'chi'", null, null, null, null);

        while (cursor.moveToNext()) {
            String groupName = cursor.getString(cursor.getColumnIndex("group_name"));
            double money = cursor.getDouble(cursor.getColumnIndex("money"));

            if (thongKe.containsKey(groupName)) {
                thongKe.put(groupName, thongKe.get(groupName) + (int) money);
            } else {
                thongKe.put(groupName, (int) money);
            }
        }
        cursor.close();

        return thongKe;
    }
}