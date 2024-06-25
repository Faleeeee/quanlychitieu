package com.example.quanlychitieuapp.tab_vi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quanlychitieuapp.db;
import com.example.quanlychitieuapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link tab3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tab3Fragment extends Fragment {

    TextView tienThu;
    TextView tienChi;
    TextView tongTien;
    ListView listView;
    ArrayList<String> arrThongKe;
    ArrayAdapter<String> adapterDB;
    db database;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public tab3Fragment() {
        // Required empty public constructor
    }

    public static tab3Fragment newInstance(String param1, String param2) {
        tab3Fragment fragment = new tab3Fragment();
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
        // Khởi tạo đối tượng database
        database = new db(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);

        tienChi = view.findViewById(R.id.tienRa);
        tienThu = view.findViewById(R.id.tienVao);
        tongTien = view.findViewById(R.id.tongTien);
        listView = view.findViewById(R.id.listView);

        addConTrols(view);
        showAll();
        return view;
    }

    private void addConTrols(View view) {
        listView = view.findViewById(R.id.listView);
        arrThongKe = new ArrayList<>();
        adapterDB = new ArrayAdapter<>(getContext(), R.layout.list_item, arrThongKe);
        listView.setAdapter(adapterDB);
    }

    private void showAll() {
        ArrayList<String> resultList = database.showAll();
        arrThongKe.clear();

        // Lấy tổng tiền thu và chi từ resultList
        int tongTienThu = Integer.parseInt(resultList.get(0));
        int tongTienChi = Integer.parseInt(resultList.get(1));

        // Xóa hai giá trị đầu tiên để lấy danh sách giao dịch
        resultList.remove(0);
        resultList.remove(0);

        arrThongKe.addAll(resultList);
        adapterDB.notifyDataSetChanged();

        tienThu.setText(String.valueOf(tongTienThu));
        tienChi.setText(String.valueOf(tongTienChi));
        tongTien.setText(String.valueOf(tongTienThu - tongTienChi));
    }
}
