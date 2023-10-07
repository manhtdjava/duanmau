package com.example.duanmau.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.Adapter.PhieuMuonAdapter;
import com.example.duanmau.Database.PhieuMuonDao;
import com.example.duanmau.Database.SachDao;
import com.example.duanmau.Database.ThanhVienDao;
import com.example.duanmau.Model.PhieuMuon;
import com.example.duanmau.Model.Sach;
import com.example.duanmau.Model.ThanhVien;
import com.example.duanmau.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class PhieuMuonFragment extends Fragment {
    RecyclerView rcv;
    PhieuMuonDao dao;
    PhieuMuonAdapter adapter;
    FloatingActionButton fab;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_phieu_muon, container, false);

        rcv =view.findViewById(R.id.rcyPhieuMuon);
        fab =view.findViewById(R.id.fabPhieuMuon);
        dao = new PhieuMuonDao(getContext());
        loadData();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogShowAddd();
            }
        });
        return view;
    }
    public void dialogShowAddd(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_addphieumuon);
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Spinner spnTV = dialog.findViewById(R.id.spnThanhVien);
        Spinner spnSach = dialog.findViewById(R.id.spnSach);
        EditText edtngay = dialog.findViewById(R.id.edtngatThueAdd);
        Button btnsubmit = dialog.findViewById(R.id.btnsubmitaddPM);
        Button btncancle = dialog.findViewById(R.id.btncanaddPM);

        getDataThanhVien(spnTV);
        getDataSach(spnSach);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtngay.getText().toString().isEmpty() ) {
                    Toast.makeText(getContext(), "Bạn không được để trống!!!", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, Object> hsTV = (HashMap<String, Object>) spnTV.getSelectedItem();
                    int matv = (int) hsTV.get("maTV");
                    HashMap<String, Object> hsSach = (HashMap<String, Object>) spnSach.getSelectedItem();
                    int masach = (int) hsSach.get("maSach");
                    int tien = (int) hsSach.get("giaThue");

                    AddPM(matv,masach, tien);
                    dialog.dismiss();

                }
            }
        });
        btncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtngay.setText("");

            }
        });
        dialog.show();
    }
    private void AddPM(int matv, int masach, int tien) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User_File", Context.MODE_PRIVATE);
        String matt = sharedPreferences.getString("Username","");
        Date currenTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngay = simpleDateFormat.format(currenTime);

        PhieuMuon phieuMuon = new PhieuMuon(matt,matv,masach,ngay,tien, 0);
        boolean check = dao.insert(phieuMuon);
        if(check){
            Toast.makeText(getContext(), "Thêm phiếu mượn thành công", Toast.LENGTH_SHORT).show();
            loadData();
            adapter.notifyDataSetChanged();
        }else{
            Toast.makeText(getContext(), "Thêm phiếu mượn thất bại", Toast.LENGTH_SHORT).show();
        }
    }
    private void getDataThanhVien(Spinner spnThanhVien){
        ThanhVienDao thanhVienDao = new ThanhVienDao(getContext());
        List<ThanhVien> list = thanhVienDao.getAll();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for(ThanhVien tv : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maTV",tv.getMaTV());
            hs.put("hoTen",tv.getHoTenTV());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"hoTen"},
                new int[]{android.R.id.text1});
        spnThanhVien.setAdapter(simpleAdapter);
    }

    private void getDataSach(Spinner spnSach){
        SachDao sachDao = new SachDao(getContext());
        List<Sach> list = sachDao.getAll();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for(Sach sc : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maSach",sc.getmSach());
            hs.put("tenSach",sc.getTenSach());
            hs.put("giaThue",sc.getGiaThue());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tenSach"},
                new int[]{android.R.id.text1});
        spnSach.setAdapter(simpleAdapter);
    }
    private void loadData(){
        List<PhieuMuon> list = dao.getAll();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);
        adapter = new PhieuMuonAdapter(getContext(), list);
        rcv.setAdapter(adapter);
    }
}