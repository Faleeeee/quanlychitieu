package com.example.quanlychitieuapp;

public class GiaoDich {
    int id;
    int id_wal;
    int money;
    String loai_giaoDich;
    String group_name;
    String date;
    String note;

    // Constructor đầy đủ
    public GiaoDich(int id, int id_wal, int money, String loai_giaoDich, String group_name, String date, String note) {
        this.id = id;  // Sửa lỗi này
        this.id_wal = id_wal;
        this.money = money;
        this.loai_giaoDich = loai_giaoDich;
        this.group_name = group_name;
        this.date = date;
        this.note = note;
    }

    public GiaoDich(int id_wal, int money, String loai_giaoDich, String group_name, String date, String note) {
        this.id_wal = id_wal;
        this.money = money;
        this.loai_giaoDich = loai_giaoDich;
        this.group_name = group_name;
        this.date = date;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public int getIdWallet() {
        return id_wal;
    }

    @Override
    public String toString() {
        return group_name + "   " + money + "   " + note + loai_giaoDich;
    }
}
