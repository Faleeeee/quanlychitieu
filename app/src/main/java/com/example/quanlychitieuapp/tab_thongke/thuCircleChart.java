package com.example.quanlychitieuapp.tab_thongke;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

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


public class thuCircleChart extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database; // Khai báo biến database

    public thuCircleChart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment chiCircleChart.
     */
    // TODO: Rename and change types and number of parameters
    public static thuCircleChart newInstance(String param1, String param2) {
        thuCircleChart fragment = new thuCircleChart();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thu_circle_chart, container, false);

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

        if (chartContainer != null) {
            chartContainer.addView(pieChart);
        } else {
            // Handle the case when chartContainer is null, maybe log an error or show a message.
            Log.e("thuCircleChart", "chartContainer is null");
            return;
        }

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
        pieChart.setCenterText("Thống kê thu");
        pieChart.getLegend().setTextColor(Color.WHITE); // Đặt màu chữ chú thích thành màu trắng
        pieChart.getLegend().setWordWrapEnabled(true); // xuong hang chu thich
        pieChart.getLegend().setTextSize(25f);
        pieChart.animateY(1000);
        pieChart.invalidate();
    }

    private Map<String, Integer> getThongKeChiTieu() {
        Map<String, Integer> thongKe = new HashMap<>();

        Cursor cursor = database.query("giaodich", null, "loai_giaodich = 'thu'", null, null, null, null);

        if (cursor != null) {
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
        } else {
            // Handle null cursor case
            Log.e("chiCircleChart", "Cursor is null");
        }

        return thongKe;
    }

}