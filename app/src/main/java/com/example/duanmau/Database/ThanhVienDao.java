package com.example.duanmau.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.Model.PhieuMuon;
import com.example.duanmau.Model.Sach;
import com.example.duanmau.Model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDao {
    private SQLiteDatabase db;

    public ThanhVienDao(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    @SuppressLint("Range")
    public List<ThanhVien> getDSThanhVien(String sql, String...selectionArgs){
        List<ThanhVien> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()){
            ThanhVien obj = new ThanhVien();
            obj.setMaTV(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maTV"))));
            obj.setHoTenTV(cursor.getString(cursor.getColumnIndex("hoTen")));
            obj.setNamSinh(cursor.getString(cursor.getColumnIndex("namSinh")));

            list.add(obj);
        }
        return list;
    }
    public List<ThanhVien> getAll(){
        String sql ="Select *from ThanhVien";
        return getDSThanhVien(sql);
    }
    public ThanhVien getID(String id){
        String sql = "Select * from ThanhVien WHERE maTV = ?";
        List<ThanhVien> list = getDSThanhVien(sql, id);
        return list.get(0);
    }
    public boolean insert(String hoten, String namsinh){
        ContentValues values = new ContentValues();
        values.put("hoTen", hoten);
        values.put("namSinh",namsinh);
        long check = db.insert("ThanhVien",null, values);
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean update(int matv,String hoten, String namsinh){
        ContentValues values = new ContentValues();
        values.put("hoTen",hoten);
        values.put("namSinh",namsinh);
        long check = db.update("ThanhVien",values,"maTV = ?",new String[]{String.valueOf(matv)});
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }

    public int delete(int matv){
        Cursor cursor = db.rawQuery("select * from PhieuMuon where maTV = ?",new String[]{String.valueOf(matv)});
        if(cursor.getCount() != 0){
            return  -1;
        }

        long check = db.delete("ThanhVien","maTV = ?",new String[]{String.valueOf(matv)});
        if(check == -1){
            return 0;
        }else{
            return 1;
        }
    }

}
