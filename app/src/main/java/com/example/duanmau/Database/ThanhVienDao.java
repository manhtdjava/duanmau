package com.example.duanmau.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.Model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDao {
    private SQLiteDatabase db;

    public ThanhVienDao(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long insert(ThanhVien obj){
        ContentValues values = new ContentValues();
        values.put("hoTen", obj.getHoTenTV());
        values.put("namSinh", obj.getNamSinh());
        return db.insert("ThanhVien", null, values);
    }
    public int update(ThanhVien obj){
        ContentValues values = new ContentValues();
        values.put("hoTen", obj.getHoTenTV());
        values.put("namSinh", obj.getNamSinh());
        return db.update("ThanhVien", values, "maTV=?", new String[]{String.valueOf(obj.getMaTV())});
    }
    public int delete(String id){
        return db.delete("ThanhVien", "maTV=?", new String[]{id});
    }

    @SuppressLint("Range")
    public List<ThanhVien> getData(String sql, String...selectionArgs){
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
    // get tat ca data
    public List<ThanhVien> getAll(){
        String sql = "SELECT * FROM ThanhVien";
        return getData(sql);
    }
    //get daât theo id
    public ThanhVien getID (String id){
        String sql = "SELECT * FROM ThanhVien WHERE maTV = ?";
        List<ThanhVien> list = getData(sql, id);
        return list.get(0);
    }

}
