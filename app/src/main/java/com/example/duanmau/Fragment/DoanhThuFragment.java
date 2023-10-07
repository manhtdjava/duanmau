package com.example.duanmau.Fragment;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.duanmau.Database.PhieuMuonDao;
import com.example.duanmau.R;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class DoanhThuFragment extends Fragment {

    Button btnTuNgay, btnDenNgay, btnDoanhThu;
    EditText edtTuNgay, edtDenNgay;
    TextView txtDoanhThu;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    int mY, mm, md;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doanh_thu, container, false);
        btnTuNgay = view.findViewById(R.id.btntuNgay);
        btnDenNgay = view.findViewById(R.id.btndenNgay);
        btnDoanhThu = view.findViewById(R.id.btndoanhThu);

        edtTuNgay = view.findViewById(R.id.edttuNgay);
        edtDenNgay = view.findViewById(R.id.edtdenNgay);
        txtDoanhThu = view.findViewById(R.id.txtdoanhThu);


        btnTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mY = c.get(Calendar.YEAR);
                mm = c.get(Calendar.MONTH);
                md = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(), 0, mDateTuNgay, mY, mm, md);
                d.show();
            }
        });

        btnDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mY = c.get(Calendar.YEAR);
                mm = c.get(Calendar.MONTH);
                md = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(), 0, mDateDenNgay, mY, mm, md);
                d.show();
            }
        });

        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tungay = edtTuNgay.getText().toString();
                String denngay = edtDenNgay.getText().toString();
                PhieuMuonDao dao = new PhieuMuonDao(getActivity());
                txtDoanhThu.setText("Doanh Thu: "+dao.getDoanhThu(tungay, denngay)+" VND");
            }
        });
        return view;
    }
    DatePickerDialog.OnDateSetListener mDateTuNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            mY = year;
            mm = month;
            md = day;
            GregorianCalendar c = new GregorianCalendar(mY, mm, md);
            edtTuNgay.setText(sdf.format(c.getTime()));
        }
    };
    DatePickerDialog.OnDateSetListener mDateDenNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            mY = year;
            mm = month;
            md = day;
            GregorianCalendar c = new GregorianCalendar(mY, mm, md);
            edtDenNgay.setText(sdf.format(c.getTime()));
        }
    };
}