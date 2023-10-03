package com.example.duanmau.Database;

public class DataSqlite {
    public static final String INSERT_THU_THU = "Insert into ThuThu(MaTT, HoTen, MatKhau) values " +
            "('tt1', 'manh', '1911')";
    public static final String INSERT_THANH_VIEN = "Insert into ThanhVien(maTV,hoTen, namSinh) values" +
            "('Trần Đức Mạnh', '2004')," +
            "('Võ Ánh Nga', '2003')"
            ;
    public static final String INSERT_LOAI_SACH = "Insert into LoaiSach(tenLoai) values" +
            "('Tiếng anh cơ bản'),"+
            "('Java')";
    public static final String INSERT_SACH = "Insert into Sach(TenSach, giaThue, maLoai) values" +
            "('Tiếng anh cơ bản', '10000','6'),"+
            "('Java', '20000','6')";
    public static final String INSERT_PHIEU_MUON = "Insert into PhieuMuon(maTT, maTV, maSach ,ngay ,tienThue, traSach) values" +
            "('tt1', '1','1','2023/10/7' ,'2000', 0 ),"+
            "('tt2', '2','2','2023/10/12' ,'3000', 1)";
}
