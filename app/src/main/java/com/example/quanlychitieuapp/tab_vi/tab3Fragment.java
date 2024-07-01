package com.example.quanlychitieuapp.tab_vi;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlychitieuapp.CustomAdapter;
import com.example.quanlychitieuapp.DatabaseHelper;
import com.example.quanlychitieuapp.R;
import com.example.quanlychitieuapp.wallet;
import com.example.quanlychitieuapp.walletHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class tab3Fragment extends Fragment {
    int userID;
    TextView tienThu;
    TextView tienChi;
    TextView tongTien;
    ListView listView;
    Spinner spinnerWallet;
    ArrayList<String> arrThongKe;
    CustomAdapter adapterDB;
    DatabaseHelper database;  // DatabaseHelper instance
    private walletHelper walletData;

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
        walletData = new walletHelper(getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);

        tienChi = view.findViewById(R.id.tienRa);
        tienThu = view.findViewById(R.id.tienVao);
        tongTien = view.findViewById(R.id.tongTien);
        listView = view.findViewById(R.id.listView);
        spinnerWallet = view.findViewById(R.id.spinnerWallet);

        walletHelper walletHelper = new walletHelper(getContext());
        ArrayList<wallet> walletList = walletHelper.showWallet();

        ArrayList<String> walletNames = new ArrayList<>();
        final Map<String, Integer> walletMap = new HashMap<>();
        for (wallet walletItem : walletList) {
            walletNames.add(walletItem.getName());
            walletMap.put(walletItem.getName(), walletItem.getIdWallet());
        }

        // Tạo ArrayAdapter sử dụng layout custom
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.style_spinner, walletNames) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView textViewName = view.findViewById(R.id.textViewName);
                textViewName.setText("Tài khoản"); // Hiển thị chữ "Tài khoản" cho mọi item

                return view;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                return getView(position, convertView, parent);
            }
        };


        addControls(view); // Ensure addControls() is called first
//        showAll(); // Call showAll() after addControls()

        spinnerWallet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedWalletName = walletNames.get(position);
                int idWalletChose = walletMap.get(selectedWalletName);
                Toast.makeText(getContext(), "Đã chọn ví: " + selectedWalletName, Toast.LENGTH_SHORT).show();
                showAll(idWalletChose);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (!walletNames.isEmpty()) {
                    String firstWalletName = walletNames.get(0);
                    int idWalletChose = walletMap.get(firstWalletName);
                    Toast.makeText(getContext(), "Đã chọn ví: " + firstWalletName, Toast.LENGTH_SHORT).show();
                    showAll(idWalletChose);
                }
            }
        });

        return view;
    }

    private void showAll(int idWalletChose) {
        ArrayList<String> resultList = database.showAll(listView, idWalletChose);
        arrThongKe.clear();

        if (resultList.size() >= 2) {
            int tongTienThu = Integer.parseInt(resultList.get(0));
            int tongTienChi = Integer.parseInt(resultList.get(1));

            resultList.remove(0);
            resultList.remove(0);

            arrThongKe.addAll(resultList);
            adapterDB.notifyDataSetChanged();

            tienThu.setText(String.valueOf(tongTienThu));
            tienChi.setText(String.valueOf(tongTienChi));
            tongTien.setText(String.valueOf(tongTienThu - tongTienChi));
        } else {
            tienThu.setText("0");
            tienChi.setText("0");
            tongTien.setText("0");
            Toast.makeText(getContext(), "Không có giao dịch nào cho ví này.", Toast.LENGTH_SHORT).show();
        }
    }


    private void addControls(View view) {
        listView = view.findViewById(R.id.listView);
        arrThongKe = new ArrayList<>();
        // Cung cấp các biểu tượng phù hợp cho CustomAdapter
        int[] icons = {
                R.drawable.baseline_fastfood_24,
                R.drawable.outline_medical_information_24,
                R.drawable.baseline_business_center_241,
                R.drawable.baseline_directions_car_filled_24,
                R.drawable.baseline_monetization_on_24
        };

        adapterDB = new CustomAdapter(getContext(), arrThongKe, icons);
        listView.setAdapter(adapterDB);
    }


}
