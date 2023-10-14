package com.example.duanmau.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.duanmau.Database.ThuThuDao;
import com.example.duanmau.MainActivity;
import com.example.duanmau.databinding.ActivityLoginBinding;


public class Login extends AppCompatActivity {
    ActivityLoginBinding binding;
   ThuThuDao thuThuDao;
   String name, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        thuThuDao = new ThuThuDao(this);

        SharedPreferences preferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user = preferences.getString("USERNAME", "");
        String passw = preferences.getString("PASSWORD", "");
        Boolean rem = preferences.getBoolean("REMEMBER", false);


        binding.edtNamedn.setText(user);
        binding.edtPassdn.setText(passw);
        binding.checkBox.setChecked(rem);

        binding.btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name= binding.edtNamedn.getText().toString();
                pass = binding.edtPassdn.getText().toString();

                if(name.trim().isEmpty()||pass.trim().isEmpty())
                    Toast.makeText(Login.this, "Bạn không được để trống!", Toast.LENGTH_SHORT).show();
                else{
                    if(thuThuDao.checkLogin(name, pass) > 0){
                        Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        remember(name, pass, binding.checkBox.isChecked());
                        Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("USERNAME", name);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(Login.this, "Thông tin không hợp lệ!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        binding.btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.edtNamedn.setText("");
                binding.edtPassdn.setText("");
            }
        });
    }
    public void remember(String u, String p, boolean status){
        SharedPreferences preferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (!status){
            editor.clear();
        }else {
            editor.putString("USERNAME", u);
            editor.putString("PASSWORD", p);
            editor.putBoolean("REMEMBER", status);

        }
        editor.commit();
    }

    }
