package com.example.duanmau.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.Model.LoaiSach;
import com.example.duanmau.Model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDao {
    private SQLiteDatabase db;

    public LoaiSachDao(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long insert(LoaiSach obj){
        ContentValues values = new ContentValues();
        values.put("tenLoai", obj.getTenLoai());
        return db.insert("LoaiSach", null, values);
    }
    public int updatel(LoaiSach obj){
        ContentValues values = new ContentValues();
        values.put("tenLoai", obj.getTenLoai());
        return db.update("LoaiSach", values, "maLoai=?", new String[]{String.valueOf(obj.getMaLoai())});
    }
    public int delete(String id){
        return db.delete("LoaiSach", "maLoai=?", new String[]{id});
    }

    @SuppressLint("Range")
    public List<LoaiSach> getData(String sql, String...selectionArgs){
        List<LoaiSach> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()){
            LoaiSach obj = new LoaiSach();
            obj.setMaLoai(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maLoai"))));
            obj.setTenLoai(cursor.getString(cursor.getColumnIndex("tenLoai")));

            list.add(obj);
        }
        return list;
    }
    // get tat ca data
    public List<LoaiSach> getAll(){
        String sql = "SELECT * FROM LoaiSach";
        return getData(sql);
    }
    //get daât theo id
    public LoaiSach getID (String id){
        String sql = "SELECT * FROM LoaiSach WHERE maLoai= ?";
        List<LoaiSach> list = getData(sql, id);
        return list.get(0);
    }

}
