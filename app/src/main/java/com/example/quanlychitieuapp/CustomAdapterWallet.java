package com.example.quanlychitieuapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

public class CustomAdapterWallet extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<GiaoDich>> listDataChild;
    private HashMap<String, Integer> listIcons;

    public CustomAdapterWallet(Context context, List<String> listDataHeader, HashMap<String, List<GiaoDich>> listDataChild, HashMap<String, Integer> listIcons) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listDataChild;
        this.listIcons = listIcons; // Lưu danh sách icon
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listDataChild.get(listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.head_listview_vi, null);
        }

        TextView lblListHeader = convertView.findViewById(R.id.groupTitle);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final GiaoDich giaoDich = (GiaoDich) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_with_icon, null);
        }

        TextView txtListChild = convertView.findViewById(R.id.text_view);
        txtListChild.setText(giaoDich.toString());

        ImageView iconImageView = convertView.findViewById(R.id.icon);
        int iconResource = getIconResourceForTransaction(giaoDich); // Lấy resource icon tương ứng
        if (iconResource != 0) {
            iconImageView.setImageResource(iconResource);
        } else {
            iconImageView.setImageResource(R.drawable.outline_comment_24); // Nếu không có icon thì sử dụng icon mặc định
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Bạn đã chọn: " + giaoDich.toString(), Toast.LENGTH_SHORT).show();

                Intent myIntent = new Intent(context, chitietchitieu.class);
                Bundle myBundle = new Bundle();
                myBundle.putInt("id_giaodich", giaoDich.getId());  // Thêm id của giao dịch vào bundle
                myBundle.putInt("id_wallet", giaoDich.getIdWallet());
                myBundle.putString("name", giaoDich.group_name);
                myBundle.putInt("money", giaoDich.money);
                myBundle.putString("loai_giaodich", giaoDich.loai_giaoDich);
                myBundle.putString("date", giaoDich.date);
                myBundle.putString("note", giaoDich.note);

                myIntent.putExtra("myPackageChiTietChiTieu", myBundle);
                context.startActivity(myIntent);
            }
        });

        return convertView;
    }

    // Phương thức này dựa trên các quy tắc của bạn để chọn icon tương ứng với từng loại giao dịch
    private int getIconResourceForTransaction(GiaoDich giaoDich) {

        // Thêm các trường hợp khác nếu cần thiết

        if (giaoDich.getGroup_name().equals("Ăn uống")) {
            return R.drawable.baseline_fastfood_24;
        } else if (giaoDich.getGroup_name().equals("Bảo hiểm")) {
            return R.drawable.outline_medical_information_24;
        } else if (giaoDich.getGroup_name().equals("Đầu tư")) {
            return R.drawable.baseline_monetization_on_24;
        } else if (giaoDich.getGroup_name().equals("Di chuyển")) {
            return R.drawable.baseline_directions_car_filled_24;
        } else if (giaoDich.getGroup_name().equals("Các chi phí khác")) {
            return R.drawable.baseline_monetization_on_24;
        } else if (giaoDich.getGroup_name().equals("Lương")) {
            return R.drawable.baseline_monetization_on_24;
        } else if (giaoDich.getGroup_name().equals("Thu nhập khác")) {
            return R.drawable.baseline_monetization_on_24;
        } else if (giaoDich.getGroup_name().equals("Tiền chuyển đến")) {
            return R.drawable.baseline_monetization_on_24;
        } else {
            return R.drawable.outline_comment_24; // Biểu tượng mặc định nếu không có loại giao dịch phù hợp
        }

//        return 0; // Trả về 0 nếu không có icon nào phù hợp
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
