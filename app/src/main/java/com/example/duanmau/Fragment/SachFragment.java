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
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.Adapter.SachApdater;
import com.example.duanmau.Database.LoaiSachDao;
import com.example.duanmau.Database.SachDao;
import com.example.duanmau.Model.LoaiSach;
import com.example.duanmau.Model.Sach;
import com.example.duanmau.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


public class SachFragment extends Fragment {
    FloatingActionButton fab;
    RecyclerView rcv;
    SachDao dao;
    SachApdater apdater;
    List<Sach> list;
    Button btntang, btngiam;
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sach, container, false);
        fab = view.findViewById(R.id.fabSach);
        rcv = view.findViewById(R.id.rcySach);
        btntang = view.findViewById(R.id.tang);
        btngiam = view.findViewById(R.id.giam);
        searchView = view.findViewById(R.id.search_bar);
        dao = new SachDao(getContext());
        loadData();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                apdater.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                apdater.getFilter().filter(text);
                return false;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    dialogShowAddd();
            }
        });

        btntang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apdater.sort();
            }
        });

        btngiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               apdater.sort2();
            }
        });

        return view;
    }
    private ArrayList<HashMap<String , Object>> getDSLoaiSach(){
        LoaiSachDao loaisach = new LoaiSachDao(getContext());
        List<LoaiSach> list1 = loaisach.getAll();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();

        for (LoaiSach ls : list1){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maLoai", ls.getMaLoai());
            hs.put("tenLoai", ls.getTenLoai());
            listHM.add(hs);
        }
        return listHM;
    }
    public void dialogShowAddd(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_addsach);
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText edtten = dialog.findViewById(R.id.edttenSach);
        EditText edtgiathue = dialog.findViewById(R.id.edtGiaThue);
        EditText edtnxb = dialog.findViewById(R.id.edtnxb);
        Spinner spnSach = dialog.findViewById(R.id.spnSachadd);
        Button btnsubmit = dialog.findViewById(R.id.btnsubmitaddSach);
        Button btncancle = dialog.findViewById(R.id.btncanaddSach);

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                getDSLoaiSach(),
                android.R.layout.simple_list_item_1,
                new String[]{"tenLoai"},
                new int[]{android.R.id.text1}
        );
        spnSach.setAdapter(simpleAdapter);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtten.getText().toString().isEmpty() || edtgiathue.getText().toString().isEmpty() ) {
                    Toast.makeText(getContext(), "Bạn không được để trống!!!", Toast.LENGTH_SHORT).show();
                } else {
                    String hoten = edtten.getText().toString();
                    String giathue = edtgiathue.getText().toString();
                    String nxb = edtnxb.getText().toString();
                    HashMap<String, Object> hs = (HashMap<String, Object>) spnSach.getSelectedItem();
                    int maloai = (int) hs.get("maLoai");

                    int tien = Integer.parseInt(giathue);
                    boolean check = dao.insert(hoten,tien, Integer.parseInt(nxb),maloai);
                    if(check){
                        loadData();
                        Toast.makeText(getContext(), "Thêm thành công sách", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else{
                        Toast.makeText(getContext(), "Thêm không thành công sách", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        btncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtten.setText("");
                edtgiathue.setText("");
                edtgiathue.setText("");
            }
        });
        dialog.show();
    }
    private void loadData(){
        list = dao.getAll();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);
        apdater = new SachApdater(getContext(),list, getDSLoaiSach());
        rcv.setAdapter(apdater);
        apdater.notifyDataSetChanged();
    }

}