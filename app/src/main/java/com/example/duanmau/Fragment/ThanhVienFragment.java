package com.example.duanmau.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.Adapter.ThanhVienAdapter;
import com.example.duanmau.Database.ThanhVienDao;
import com.example.duanmau.Model.ThanhVien;
import com.example.duanmau.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienFragment extends Fragment {

    RecyclerView rcyThanhVien;
    List<ThanhVien> list;
    ThanhVienAdapter adapter;
    ThanhVienDao thanhVienDao;
    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thanh_vien, container, false);
        rcyThanhVien = view.findViewById(R.id.rcyThanhVien);
        fab = view.findViewById(R.id.fab);

        thanhVienDao = new ThanhVienDao(getContext());
        getData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogShowAddd();
            }
        });
        return view;
    }
    void getData(){
        list = thanhVienDao.getDSThanhVien();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcyThanhVien.setLayoutManager(linearLayoutManager);
        adapter = new ThanhVienAdapter(getContext(), list);
        rcyThanhVien.setAdapter(adapter);

    }
    public void dialogShowAddd(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_add);
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText edtten = dialog.findViewById(R.id.edtten);
        EditText edtnamSinh = dialog.findViewById(R.id.edtnamSinh);
        Button btnsubmit = dialog.findViewById(R.id.btnsubmitadd);
        Button btncancle = dialog.findViewById(R.id.btncanadd);


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtten.getText().toString().isEmpty() || edtnamSinh.getText().toString().isEmpty() ) {
                    Toast.makeText(getContext(), "Bạn không được để trống!!!", Toast.LENGTH_SHORT).show();
                } else {
                    String hoten = edtten.getText().toString();
                    String namsinh = edtnamSinh.getText().toString();

                    boolean check  = thanhVienDao.insert(hoten,namsinh);
                    if(check){
                        getData();
                        Toast.makeText(getContext(), "Thêm nhân viên thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else{
                        Toast.makeText(getContext(), "Thêm nhân viên thất bại", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        btncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtten.setText("");
                edtnamSinh.setText("");
            }
        });
        dialog.show();
    }
}