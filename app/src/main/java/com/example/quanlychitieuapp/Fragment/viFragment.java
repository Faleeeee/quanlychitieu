package com.example.quanlychitieuapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.quanlychitieuapp.R;
import com.example.quanlychitieuapp.tab_vi.tabPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

public class viFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View mView;
    private static final int NUM_WEEKS = 52; // hoặc số tuần mà bạn muốn hiển thị
    private int currentWeekIndex; // chỉ số của tuần hiện tại

    public viFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_vi, container, false);

        tabLayout = mView.findViewById(R.id.tab_layout);
        viewPager = mView.findViewById(R.id.vi_viewpager);

        // Tính chỉ số của tuần hiện tại (ví dụ là tuần hiện tại là tuần 26)
        Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);

        // Khởi tạo adapter và set cho ViewPager
        tabPagerAdapter adapter = new tabPagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, NUM_WEEKS);
        viewPager.setAdapter(adapter);

        // Đặt tab của tuần hiện tại là tab mặc định khi vào ứng dụng
        currentWeekIndex = currentWeek - 1; // vì currentWeek bắt đầu từ 1
        viewPager.setCurrentItem(currentWeekIndex);

        tabLayout.setupWithViewPager(viewPager);

        return mView;
    }
}
