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
    public long insert(Sach obj){
        ContentValues values = new ContentValues();
        values.put("tenSach", obj.getTenSach());
        values.put("giaThue", obj.getGiaThue());
        values.put("maLoai", obj.getMaLoai());
        return db.insert("Sach", null, values);
    }
    public int updatel(Sach obj){
        ContentValues values = new ContentValues();
        values.put("tenSach", obj.getTenSach());
        values.put("giaThue", obj.getGiaThue());
        values.put("maLoai", obj.getMaLoai());
        return db.update("Sach", values, "maSach=?", new String[]{String.valueOf(obj.getmSach())});
    }
    public int delete(String id){
        return db.delete("Sach", "maSach=?", new String[]{id});
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
