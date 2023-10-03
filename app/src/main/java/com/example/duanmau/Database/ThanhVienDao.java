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
   DbHelper dbHelper;

    public ThanhVienDao(Context context){
         dbHelper = new DbHelper(context);
    }
    public List<ThanhVien> getDSThanhVien(){
        List<ThanhVien> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ThanhVien",null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new ThanhVien(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean insert(String hoten, String namsinh){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
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
        SQLiteDatabase db = dbHelper.getWritableDatabase();
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
        SQLiteDatabase db = dbHelper.getWritableDatabase();
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
