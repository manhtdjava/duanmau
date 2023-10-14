package com.example.duanmau.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.duanmau.Database.ThuThuDao;
import com.example.duanmau.Model.ThuThu;
import com.example.duanmau.R;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaoTaiKhoanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaoTaiKhoanFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TaoTaiKhoanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaoTaiKhoanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaoTaiKhoanFragment newInstance(String param1, String param2) {
        TaoTaiKhoanFragment fragment = new TaoTaiKhoanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    ThuThuDao thuThuDAO;
    TextInputEditText edt_username,edt_name,edt_password,edt_enterthepassword;
    Button btn_cancel,btn_save;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tao_tai_khoan, container, false);

        thuThuDAO = new ThuThuDao(getContext());
        initUI(view);

        btnCancel();
        btnSave();

        return view;
    }

    private void btnCancel() {
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_username.setText("");
                edt_name.setText("");
                edt_password.setText("");
                edt_enterthepassword.setText("");
            }
        });
    }

    private void btnSave() {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edt_username.getText().toString().trim();
                String name = edt_name.getText().toString().trim();
                String password = edt_password.getText().toString().trim();
                String enterPass = edt_enterthepassword.getText().toString().trim();


                if (validate(username,name,password,enterPass) > 0) {
                    ThuThuDao thuThuDAO = new ThuThuDao(getContext());
                    if (thuThuDAO.insert(new ThuThu(username,name,password)) > 0) {
                        Toast.makeText(getContext(), "Thêm thành công !", Toast.LENGTH_SHORT).show();
                        edt_username.setText("");
                        edt_name.setText("");
                        edt_password.setText("");
                        edt_enterthepassword.setText("");
                    } else {
                        Toast.makeText(getContext(), "Thêm thất bại !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public int validate(String username,String name,String password,String enterPass) {
        int check = 1;
        if (username.length() != 0 && name.length() != 0 && password.length() != 0 && enterPass.length() != 0) {
            if (!password.equals(enterPass)) {
                Toast.makeText(getContext(), "Nhập lại mật khẩu chưa chính xác !", Toast.LENGTH_SHORT).show();
                edt_enterthepassword.setError("Nhập lại mật khẩu chưa chính xác !");
                edt_enterthepassword.requestFocus();
                check = -1;
            } else {
                check = 1;
            }
        } else {
            if (username.length() == 0) {
                edt_username.setError("Không được để trống trường này !");
                edt_username.requestFocus();
            }
            if (name.length() == 0) {
                edt_name.setError("Không được để trống trường này !");
                edt_name.requestFocus();
            }
            if (password.length() == 0) {
                edt_password.setError("Không được để trống trường này !");
                edt_password.requestFocus();
            }
            if (enterPass.length() == 0) {
                edt_enterthepassword.setError("Không được để trống trường này !");
                edt_enterthepassword.requestFocus();
            }
            check = -1;
        }
        return check;
    }
    private void initUI(View view) {
        edt_username = view.findViewById(R.id.edt_username);
        edt_name = view.findViewById(R.id.edt_name);
        edt_password = view.findViewById(R.id.edt_password);
        edt_enterthepassword = view.findViewById(R.id.edt_enterthepassword);

        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_save = view.findViewById(R.id.btn_save);
    }
}