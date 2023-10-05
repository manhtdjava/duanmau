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

import com.example.duanmau.Adapter.LoaiSachAdapter;
import com.example.duanmau.Adapter.SachApdater;
import com.example.duanmau.Database.LoaiSachDao;
import com.example.duanmau.Model.LoaiSach;
import com.example.duanmau.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class LoaiSachFragment extends Fragment {
   List<LoaiSach> list;
   LoaiSachDao dao;
   LoaiSachAdapter adapter;
   RecyclerView rcv;
   FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loai_sach, container, false);
        rcv = view.findViewById(R.id.rcyLoaiSach);
        fab = view.findViewById(R.id.fabLoaiSach);
        dao = new LoaiSachDao(getContext());
        getData();
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
        dialog.setContentView(R.layout.dialog_addloaisach);
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText edtten = dialog.findViewById(R.id.edttenLoaiSach);
        Button btnsubmit = dialog.findViewById(R.id.btnsubmitaddLoaiSach);
        Button btncancle = dialog.findViewById(R.id.btncanaddLoaiSach);


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtten.getText().toString().isEmpty() ) {
                    Toast.makeText(getContext(), "Bạn không được để trống!!!", Toast.LENGTH_SHORT).show();
                } else {
                    String hoten = edtten.getText().toString();

                    if(dao.insert(hoten)){
                        getData();
                        Toast.makeText(getContext(), "Thêm loại sách thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else{
                        Toast.makeText(getContext(), "Thêm loại sách không thành công", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        btncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtten.setText("");
            }
        });
        dialog.show();
    }
    public void getData(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);
        list = dao.getAll();
        adapter = new LoaiSachAdapter(getContext(), list);
        rcv.setAdapter(adapter);
    }

}