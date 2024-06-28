package com.example.quanlychitieuapp.tab_vi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.quanlychitieuapp.CustomAdapter;
import com.example.quanlychitieuapp.DatabaseHelper;
import com.example.quanlychitieuapp.R;

import java.util.ArrayList;

public class tab3Fragment extends Fragment {

    TextView tienThu;
    TextView tienChi;
    TextView tongTien;
    ListView listView;
    ArrayList<String> arrThongKe;
    CustomAdapter adapterDB;
    DatabaseHelper database;  // DatabaseHelper instance

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
        // Initialize the DatabaseHelper instance
        database = new DatabaseHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);

        tienChi = view.findViewById(R.id.tienRa);
        tienThu = view.findViewById(R.id.tienVao);
        tongTien = view.findViewById(R.id.tongTien);
        listView = view.findViewById(R.id.listView);

        addControls(view);
        showAll();
        return view;
    }

    private void addControls(View view) {
        listView = view.findViewById(R.id.listView);
        arrThongKe = new ArrayList<>();
        int[] icons = new int[arrThongKe.size()];
        adapterDB = new CustomAdapter(getContext(), arrThongKe, icons);
        listView.setAdapter(adapterDB);
    }

    private void showAll() {
        ArrayList<String> resultList = database.showAll(listView);
        arrThongKe.clear();

        // Get total income and expenses from resultList
        int tongTienThu = Integer.parseInt(resultList.get(0));
        int tongTienChi = Integer.parseInt(resultList.get(1));

        // Remove the first two values to get the transaction list
        resultList.remove(0);
        resultList.remove(0);

        arrThongKe.addAll(resultList);
        adapterDB.notifyDataSetChanged();

        tienThu.setText(String.valueOf(tongTienThu));
        tienChi.setText(String.valueOf(tongTienChi));
        tongTien.setText(String.valueOf(tongTienThu - tongTienChi));
    }
}
