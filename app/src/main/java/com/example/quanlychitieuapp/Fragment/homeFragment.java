package com.example.quanlychitieuapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.quanlychitieuapp.CustomAdapter;
import com.example.quanlychitieuapp.DatabaseHelper;
import com.example.quanlychitieuapp.R;
import com.example.quanlychitieuapp.wallet;
import com.example.quanlychitieuapp.walletAdapter;
import com.example.quanlychitieuapp.walletHelper;
import com.example.quanlychitieuapp.tab_home.tabHomeAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homeFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View mView;
    private ListView listView;
    private DatabaseHelper database;
    private walletHelper walletData;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public homeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static homeFragment newInstance(String param1, String param2) {
        homeFragment fragment = new homeFragment();
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
        database = new DatabaseHelper(getActivity());
        walletData = new walletHelper(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        listView = mView.findViewById(R.id.lvWallet);
        tabLayout = mView.findViewById(R.id.tab_baocao_home);
        viewPager = mView.findViewById(R.id.baocao_viewpager);

        tabHomeAdapter adapter = new tabHomeAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        // Set up the ListView with wallet data
        setListViewWallet();


        return mView;
    }


    public void setListViewWallet() {
        int icon = R.drawable.baseline_account_balance_wallet_24; // Chọn một icon duy nhất

        ArrayList<wallet> walletList = walletData.showWallet(); // Lấy danh sách các ví từ cơ sở dữ liệu

        // Tạo danh sách tên các ví để hiển thị
        ArrayList<String> walletNames = new ArrayList<>();
        for (wallet walletItem : walletList) {
            walletNames.add(walletItem.getName());
        }

        // Tạo adapter cho ListView
        ArrayAdapter<String> arrayAdapter = new walletAdapter(getContext(), walletNames, icon);
        listView.setAdapter(arrayAdapter);
    }


}
