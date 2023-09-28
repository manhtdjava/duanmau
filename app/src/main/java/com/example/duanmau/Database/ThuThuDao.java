package com.example.duanmau.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.Model.ThanhVien;
import com.example.duanmau.Model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDao {
    private SQLiteDatabase db;

    public ThuThuDao(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long insert(ThuThu obj){
        ContentValues values = new ContentValues();
        values.put("maTT", obj.getMaTT());
        values.put("hoTen", obj.getHoTen());
        values.put("matKhau", obj.getMatKhau());
        return db.insert("ThuThu", null, values);
    }
    public int updatePass(ThuThu obj){
        ContentValues values = new ContentValues();
        values.put("hoTen", obj.getHoTen());
        values.put("matKhau", obj.getMatKhau());
        return db.update("ThuThu", values, "maTT=?", new String[]{obj.getMaTT()});
    }
    public int delete(String id){
        return db.delete("ThuThu", "maTT=?", new String[]{id});
    }

    @SuppressLint("Range")
    public List<ThuThu> getData(String sql, String...selectionArgs){
        List<ThuThu> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()){
            ThuThu obj = new ThuThu();
            obj.setMaTT(cursor.getString(cursor.getColumnIndex("maTT")));
            obj.setHoTen(cursor.getString(cursor.getColumnIndex("hoTen")));
            obj.setMatKhau(cursor.getString(cursor.getColumnIndex("matKhau")));

            list.add(obj);
        }
        return list;
    }
    // get tat ca data
    public List<ThuThu> getAll(){
        String sql = "SELECT * FROM ThuThu";
        return getData(sql);
    }
    //get daât theo id
    public ThuThu getID (String id){
        String sql = "SELECT * FROM ThuThu WHERE maTT= ?";
        List<ThuThu> list = getData(sql, id);
        return list.get(0);
    }
    public int checkLogin(String id, String password){
        String sql = "SELECT * FROM ThuThu WHERE maTT = ? AND matKhau=?";
        List<ThuThu> list = getData(sql, password);
        if (list.size() == 0 ) return -1;
        return 1;
    }

}
