package com.example.quanlychitieuapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanlychitieuapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link thongKeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class thongKeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private BarChart barChart;

    public thongKeFragment() {
        // Required empty public constructor
    }

    public static thongKeFragment newInstance(String param1, String param2) {
        thongKeFragment fragment = new thongKeFragment();
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
        View view = inflater.inflate(R.layout.fragment_thong_ke, container, false);
        barChart = view.findViewById(R.id.barChart);
        setupBarChart();
        return view;
    }

    private void setupBarChart() {
        List<BarEntry> entries = new ArrayList<>();
        // Thêm dữ liệu vào entries
        entries.add(new BarEntry(1f, 1000f));
        entries.add(new BarEntry(2f, 2000f));
        entries.add(new BarEntry(3f, 1500f));

        BarDataSet dataSet = new BarDataSet(entries, "Chi tiêu");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData barData = new BarData(dataSet);

        barChart.setData(barData);
        barChart.invalidate(); // refresh

        // Cấu hình XAxis nếu cần
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return "Tháng " + (int) value;
            }
        });
    }
}
