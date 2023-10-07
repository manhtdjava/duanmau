package com.example.duanmau.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.Model.PhieuMuon;
import com.example.duanmau.Model.Sach;
import com.example.duanmau.Model.Top;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDao {
    private SQLiteDatabase db;
    private Context context;
//    SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");

    public PhieuMuonDao(Context context){
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public boolean insert(PhieuMuon obj){
        ContentValues values = new ContentValues();
        values.put("maTT", obj.getMaTT());
        values.put("maTV", obj.getMaTV());
        values.put("maSach", obj.getMaSach());
        values.put("ngay", obj.getNgay());
        values.put("giaThue", obj.getTienThue());
        values.put("traSach", obj.getTraSach());

        long check = db.insert("PhieuMuon",null,values);
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean updatep(int maPM, int matv, int masach, int chkcheck, String ngay){
        ContentValues values = new ContentValues();
        values.put("maTV", matv);
        values.put("maSach", masach);
        values.put("ngay", ngay);
        values.put("traSach", chkcheck);

        long check = db.update("PhieuMuon",values,"maPM = ?",new String[]{String.valueOf(maPM)});
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean delete(int id){
        long check = db.delete("PhieuMuon","maPM = ?",new String[]{String.valueOf(id)});
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean updateTrangThai(int mapm){
        ContentValues values = new ContentValues();
        values.put("traSach",1);
        long check = db.update("PhieuMuon",values,"maPM = ?",new String[]{String.valueOf(mapm)});
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }

    @SuppressLint("Range")
    public List<PhieuMuon> getData(String sql, String...selectionArgs){
        List<PhieuMuon> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()){
            PhieuMuon obj = new PhieuMuon();
            obj.setMaPM(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maPM"))));
            obj.setMaTT(cursor.getString(cursor.getColumnIndex("maTT")));
            obj.setMaTV(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maTV"))));
            obj.setMaSach(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maSach"))));
            obj.setTienThue(Integer.parseInt(cursor.getString(cursor.getColumnIndex("giaThue"))));
            obj.setNgay(cursor.getString(cursor.getColumnIndex("ngay")));
            obj.setTraSach(Integer.parseInt(cursor.getString(cursor.getColumnIndex("traSach"))));
            list.add(obj);
        }
        return list;
    }
    // get tat ca data
    public List<PhieuMuon> getAll(){
        String sql = "SELECT * FROM PhieuMuon";
        return getData(sql);
    }
    //get daât theo id
    public PhieuMuon getID (String id){
        String sql = "SELECT * FROM PhieuMuon WHERE maPM= ?";
        List<PhieuMuon> list = getData(sql, id);
        return list.get(0);
    }
    @SuppressLint("Range")
    public List<Top> getTop(){
        String sqlTop = "SELECT maSach, count(maSach) as soLuong FROM PhieuMuon GROUP BY maSach ORDER BY soLuong DESC LIMIT 10";
        List<Top> list = new ArrayList<Top>();
        SachDao sachDao = new SachDao(context);
        Cursor cursor = db.rawQuery(sqlTop, null);
        while (cursor.moveToNext()){
            Top top = new Top();
           Sach sach = sachDao.getID(cursor.getString(cursor.getColumnIndex("maSach")));
           top.setTenSach(sach.getTenSach());
           top.setSoLuong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soLuong"))));
            list.add(top);
        }
        return list;
    }

    @SuppressLint("Range")
    public int getDoanhThu(String tuNgay, String denNgay){
        String sqlDoanhThu = "SELECT SUM(giaThue) AS doanhThu FROM PhieuMuon WHERE ngay BETWEEN ? AND ?";
        List<Integer> list = new ArrayList<Integer>();
        Cursor cursor = db.rawQuery(sqlDoanhThu, new String[]{tuNgay, denNgay});
        while (cursor.moveToNext()){
           try{
               list.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("doanhThu"))));
           }catch (Exception e){
               list.add(0);
           }
        }
        return list.get(0);
    }

}
