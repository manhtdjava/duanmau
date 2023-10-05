package com.example.duanmau.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    static final String dbName = "PNLIB";
    static final int dbVersion = 11;
    public DbHelper(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tao bang Thu Thu
        String createTableThuThu=
                "create table ThuThu (" +
                        "maTT TEXT PRIMARY KEY, " +
                        "hoTen TEXT NOT NULL, " +
                        "matKhau TEXT NOT NULL)";

        db.execSQL(createTableThuThu);
        //Tao bang Thanh Vien
        String createTableThanhVien=
                "create table ThanhVien (" +
                        "maTV INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "hoTen TEXT NOT NULL, " +
                        "namSinh TEXT NOT NULL)";
        db.execSQL(createTableThanhVien);
        //Tao bang Phieu Muon
        String createTablePhieuMuon=
                "create table PhieuMuon(" +
                        "maPM INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "maTT TEXT NOT NULL, " +
                        "maTV INTEGER NOT NULL, " +
                        "maSach INTEGER NOT NULL, " +
                        "ngay DATE NOT NULL, " +
                        "tienThue INTEGER NOT NULL, " +
                        "traSach INTEGER NOT NULL)";
        db.execSQL(createTablePhieuMuon);
        //Tao bang Sach
        String createTableSach=
                "create table Sach(" +
                        "maSach INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tenSach TEXT NOT NULL, " +
                        "giaThue INTEGER NOT NULL, " +
                        "maLoai INTEGER NOT NULL)";
        db.execSQL(createTableSach);
        //Tao bang Loai Sach
        String createTableLoaiSach=
                "create table LoaiSach(" +
                        "maLoai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tenLoai TEXT NOT NULL)";
        db.execSQL(createTableLoaiSach);

        String createTableTop=
                "create table Top(" +
                        "tenSach TEXT PRIMARY KEY, " +
                        "soLuong INTEGER NOT NULL)";
        db.execSQL(createTableTop);

        db.execSQL(DataSqlite.INSERT_THU_THU);
        db.execSQL(DataSqlite.INSERT_THANH_VIEN);
        db.execSQL(DataSqlite.INSERT_LOAI_SACH);
        db.execSQL(DataSqlite.INSERT_SACH);
        db.execSQL(DataSqlite.INSERT_PHIEU_MUON);
////        db.execSQL("INSERT INTO LoaiSach VALUES (1, 'Thiếu nhi'),(2,'Tình cảm'),(3, 'Giáo khoa')");
////        db.execSQL("INSERT INTO Sach VALUES (1, 'Hãy đợi đấy', 2500, 1), (2, 'Thằng cuội', 1000, 1), (3, 'Lập trình Android', 2000, 3)");
////        db.execSQL("INSERT INTO ThuThu VALUES ('thuthu01','Nguyễn Văn Anh','1234','Admin'),('thuthu02','Trần Văn Hùng','123abc','ThuThu')");
//        db.execSQL("INSERT INTO ThanhVien VALUES (1,'Trần Đức Mạnh','2004')");
//        //trả sách: 1: đã trả - 0: chưa trả
////        db.execSQL("INSERT INTO PhieuMuon VALUES (1,'thuthu01',1, 1, 2500, 1, '19/03/2022')," +
////                "(2,'thuthu01',1, 3, 2000, 0, '19/03/2022')," +
////                "(3,'thuthu02',2, 1, 2000, 1, '19/03/2022')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableLoaiThuThu = "Drop table if exists ThuThu";
        String dropTableLoaiThanhVien = "Drop table if exists ThanhVien";
        String dropTableLoaiPhieuMuon = "Drop table if exists PhieuMuon";
        String dropTableLoaiSach = "Drop table if exists Sach";
        String dropTableLoaiLoaiSach = "Drop table if exists LoaiSach";
        String dropTableLoaiTop = "Drop table if exists Top";

        if (oldVersion != newVersion) {
            db.execSQL(dropTableLoaiThuThu);
            db.execSQL(dropTableLoaiThanhVien);
            db.execSQL(dropTableLoaiPhieuMuon);
            db.execSQL(dropTableLoaiSach);
            db.execSQL(dropTableLoaiLoaiSach);
            db.execSQL(dropTableLoaiTop);

            onCreate(db);
        }
    }
}












