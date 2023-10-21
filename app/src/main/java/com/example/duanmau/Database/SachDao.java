package com.example.duanmau.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.Model.LoaiSach;
import com.example.duanmau.Model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDao {
    private SQLiteDatabase db;

    public SachDao(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public boolean insert(String tensach, int tienthue,int nxb ,int maloai){
        ContentValues values = new ContentValues();
        values.put("tenSach",tensach);
        values.put("giaThue",tienthue);
        values.put("nxb",nxb);
        values.put("maLoai",maloai);
        long check = db.insert("Sach",null,values);
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean update(int masach, String tensach, int giathue,int nxb, int maloai){
        ContentValues values = new ContentValues();
        values.put("tenSach",tensach);
        values.put("giaThue",giathue);
        values.put("nxb",nxb);
        values.put("maLoai",maloai);
        long check = db.update("Sach",values,"maSach = ?", new String[]{String.valueOf(masach)});
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }
    public int delete(int masach){
        Cursor cursor = db.rawQuery("select * from PhieuMuon where maSach = ?",new String[]{String.valueOf(masach)});
        if(cursor.getCount() != 0){
            return -1;
        }
        long check = db.delete("Sach","masach = ?", new String[]{String.valueOf(masach)});
        if(check == -1){
            return 0;
        }else{
            return 1;
        }
    }

    @SuppressLint("Range")
    public List<Sach> getData(String sql, String...selectionArgs){
        List<Sach> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()){
            Sach obj = new Sach();
            obj.setmSach(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maSach"))));
            obj.setTenSach(cursor.getString(cursor.getColumnIndex("tenSach")));
            obj.setGiaThue(Integer.parseInt(cursor.getString(cursor.getColumnIndex("giaThue"))));
            obj.setNxb(Integer.parseInt(cursor.getString(cursor.getColumnIndex("nxb"))));
            obj.setMaLoai(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maLoai"))));

            list.add(obj);
        }
        return list;
    }
    // get tat ca data
    public List<Sach> getAll(){
        String sql = "SELECT * FROM Sach";
        return getData(sql);
    }
    //get daât theo id
    public Sach getID (String id){
        String sql = "SELECT * FROM Sach WHERE maSach= ?";
        List<Sach> list = getData(sql, id);
        return list.get(0);
    }
}
