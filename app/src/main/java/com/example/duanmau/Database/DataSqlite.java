package com.example.duanmau.Database;

public class DataSqlite {
    public static final String INSERT_THU_THU = "Insert into ThuThu(MaTT, HoTen, MatKhau) values " +
            "('tt1', 'manh', '1911')";
    public static final String INSERT_THANH_VIEN = "Insert into ThanhVien(maTV ,hoTen, namSinh) values" +
            "('1','Trần Đức Mạnh', '2004')"
            ;
    public static final String INSERT_LOAI_SACH = "Insert into LoaiSach(maLoai,tenLoai) values" +
            "('1','Tiếng anh')"
            ;
    public static final String INSERT_SACH = "Insert into Sach( maSach,TenSach, giaThue, maLoai) values" +
            "('1','Tiếng anh cơ bản', '10000','1')";
    public static final String INSERT_PHIEU_MUON = "Insert into PhieuMuon(maTT, maTV, maSach ,ngay ,tienThue, traSach) values" +
            "('tt1', '1','1','2023/10/7' ,'2000', 0 )";
}
