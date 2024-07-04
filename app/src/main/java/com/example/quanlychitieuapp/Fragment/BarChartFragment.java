package com.example.quanlychitieuapp.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.quanlychitieuapp.DatabaseHelper;
import com.example.quanlychitieuapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarChartFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bar_chart_fragment, container, false);

        dbHelper = new DatabaseHelper(getContext());
        database = dbHelper.getReadableDatabase();

        createBarChart(view);

        return view;
    }

    private void createBarChart(View view) {
        Map<String, Integer> thongKe = getThongKeChiTieu();
        int tongTienChi = 0;

        BarChart barChart = view.findViewById(R.id.barChart);

        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        int i = 0; // Biến đếm để thêm màu sắc
        for (Map.Entry<String, Integer> entry : thongKe.entrySet()) {
            entries.add(new BarEntry(i, entry.getValue()));
            labels.add(entry.getKey());
            tongTienChi += entry.getValue();
            i++;
        }

        BarDataSet dataSet = new BarDataSet(entries, "");
        dataSet.setColors(getColors()); // Sử dụng danh sách màu sắc đã tạo
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(12f);
        dataSet.setDrawValues(false); // Xóa chữ trên cột

        BarData data = new BarData(dataSet);
        barChart.setData(data);
        barChart.getDescription().setEnabled(false);
        barChart.setFitBars(true);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        // Ẩn legend mặc định của BarChart
        barChart.getLegend().setEnabled(false);

        barChart.getXAxis().setTextColor(Color.WHITE);
        barChart.getAxisLeft().setTextColor(Color.WHITE);
        barChart.getAxisRight().setTextColor(Color.WHITE);

        barChart.invalidate();

        TextView tvTongTienChi = view.findViewById(R.id.tvTongTienChi);
        tvTongTienChi.setText("Tổng tiền chi: " + tongTienChi);
        tvTongTienChi.setTextColor(Color.WHITE);

        // Hiển thị chú thích chi tiết
        TextView tvChuThich = view.findViewById(R.id.tvChuThich);
        StringBuilder chuThichText = new StringBuilder("Chú thích:\n");
        for (i = 0; i < labels.size(); i++) {
            chuThichText.append(" - ")
                    .append(labels.get(i))
                    .append(": <font color=\"")
                    .append(String.format("#%06X", (0xFFFFFF & getColors().get(i)))) // Chuyển đổi màu sang mã hex
                    .append("\">■</font><br>");
        }
        tvChuThich.setText(Html.fromHtml(chuThichText.toString()));
    }

    // Danh sách màu sắc cho các cột
    private List<Integer> getColors() {
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.CYAN);
        colors.add(Color.MAGENTA);
        return colors;
    }
    private Map<String, Integer> getThongKeChiTieu() {
        Map<String, Integer> thongKe = new HashMap<>();

        if (database != null) {
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
        }

        return thongKe;
    }
}