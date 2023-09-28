package com.example.duanmau.Model;

public class Sach {
    private int mSach;
    private String tenSach;
    private int giaThue, maLoai;

    public Sach(int mSach, String tenSach, int giaThue, int maLoai) {
        this.mSach = mSach;
        this.tenSach = tenSach;
        this.giaThue = giaThue;
        this.maLoai = maLoai;
    }

    public Sach() {
    }

    public int getmSach() {
        return mSach;
    }

    public void setmSach(int mSach) {
        this.mSach = mSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }
}
