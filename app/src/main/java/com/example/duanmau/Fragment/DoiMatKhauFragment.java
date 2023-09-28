package com.example.duanmau.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.duanmau.Database.ThuThuDao;
import com.example.duanmau.Model.ThuThu;
import com.example.duanmau.R;
import com.google.android.material.textfield.TextInputEditText;


public class DoiMatKhauFragment extends Fragment {
    TextInputEditText edtpasscu, edtpassmoi, edtpassconfirm;
    Button btnSave, btnCancel;
    ThuThuDao dao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
        edtpasscu = view.findViewById(R.id.edtpasscu);
        edtpassmoi = view.findViewById(R.id.edtpassmoi);
        edtpassconfirm = view.findViewById(R.id.edtpassconfirm);
        btnSave = view.findViewById(R.id.btnluu);
        btnCancel = view.findViewById(R.id.btnback);

        dao = new ThuThuDao(getActivity());
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtpasscu.setText("");
                edtpassmoi.setText("");
                edtpassconfirm.setText("");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doimk();
            }
        });
        return view;
    }

    public void doimk(){
        SharedPreferences preferences = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user = preferences.getString("USERNAME", "");
        if (validate()> 0){
            ThuThu thu = dao.getID(user);
            thu.setMatKhau(edtpassmoi.getText().toString());
            dao.updatePass(thu);
            if (dao.updatePass(thu) > 0){
                Toast.makeText(getContext(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                edtpasscu.setText("");
                edtpassmoi.setText("");
                edtpassconfirm.setText("");
            }else {
                Toast.makeText(getContext(), "Thay đổi mật khẩu không thành công", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public int validate(){
        int check = 1;
        if (edtpasscu.getText().length() ==0 || edtpassmoi.getText().length()==0|| edtpassconfirm.getText().length()==0){
            Toast.makeText(getContext(), "Bạn phải nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }else {
            SharedPreferences preferences = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);
            String passold = preferences.getString("PASS", "");
            String pass = edtpassmoi.getText().toString();
            String passconfirm = edtpassconfirm.getText().toString();
            if (!passold.equals(edtpasscu.getText().toString())){
                Toast.makeText(getContext(), "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
                check = -1;
            }else if (!pass.equals(passconfirm)){
                Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check =-1;
            }
        }
        return check;
    }
}