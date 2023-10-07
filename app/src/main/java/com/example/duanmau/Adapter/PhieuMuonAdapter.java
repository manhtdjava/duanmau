package com.example.duanmau.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.Database.PhieuMuonDao;
import com.example.duanmau.Database.SachDao;
import com.example.duanmau.Database.ThanhVienDao;
import com.example.duanmau.Model.PhieuMuon;
import com.example.duanmau.Model.Sach;
import com.example.duanmau.Model.ThanhVien;
import com.example.duanmau.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.PhieuMuonViewHolder>{
    Context context;
    List<PhieuMuon> list;
    PhieuMuonDao dao;

    public PhieuMuonAdapter(Context context, List<PhieuMuon> list) {
        this.context = context;
        this.list = list;
       dao = new PhieuMuonDao(context);
    }

    @NonNull
    @Override
    public PhieuMuonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_phieu_muon, parent, false);
        return new PhieuMuonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonViewHolder holder, int position) {
        PhieuMuon phieuMuon = list.get(position);

        holder.txtma.setText("Mã PM: "+ phieuMuon.getMaPM());

        //thành viên
        ThanhVienDao thanhVienDao = new ThanhVienDao(context);
        ThanhVien thanhVien = thanhVienDao.getID(String.valueOf(phieuMuon.getMaTV()));
        holder.txttenTV.setText("Thành viên: "+ thanhVien.getHoTenTV());

        //ten sách
        SachDao sachDao = new SachDao(context);
        Sach sach = sachDao.getID(String.valueOf(phieuMuon.getMaSach()));
        holder.txttenSach.setText("Tên sách: "+ sach.getTenSach());
        holder.txttienThue.setText("Tiền thuê: "+ sach.getGiaThue());

        holder.txtngay.setText("Ngày thuê: "+ phieuMuon.getNgay());
        String trangthai = "";
        if(list.get(position).getTraSach() == 1){
            trangthai = "Đã trả sách";
            holder.txttrangThai.setTextColor(ContextCompat.getColor(context, R.color.levente));
        }else{
            trangthai = "chưa trả sách";
            holder.txttrangThai.setTextColor(ContextCompat.getColor(context, R.color.red_A400));
        }
        holder.txttrangThai.setText(trangthai);

        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Bạn thật sự muốn xóa phiếu mượn này chứ");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PhieuMuonDao dao = new PhieuMuonDao(context);
                        boolean check = dao.delete(list.get(holder.getAdapterPosition()).getMaPM());
                        if (check){
                            list.clear();
                            list = dao.getAll();
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa phiếu mượn thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Xóa phiếu mượn không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Hủy",null);
                builder.create().show();
            }
        });
        holder.itemPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogShowUpdate(phieuMuon);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class PhieuMuonViewHolder extends RecyclerView.ViewHolder {
        TextView txtma, txttenTV, txttenSach, txttienThue, txttrangThai, txtngay;
        ImageButton imgdelete;
        CardView itemPM;
        public PhieuMuonViewHolder(@NonNull View itemView) {
            super(itemView);

            txtma = itemView.findViewById(R.id.txtmaPM);
            txttenTV = itemView.findViewById(R.id.txttenTVPM);
            txttenSach = itemView.findViewById(R.id.txttenSachPM);
            txttienThue = itemView.findViewById(R.id.txttienThue2);
            txttrangThai = itemView.findViewById(R.id.txttrangThai);
            txtngay = itemView.findViewById(R.id.txtngayThue);
            imgdelete = itemView.findViewById(R.id.imgDeletePM);
            itemPM = itemView.findViewById(R.id.itemPM);
        }
    }

    public void dialogShowUpdate(PhieuMuon phieuMuon) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_update_phieumuon);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Spinner spnTV = dialog.findViewById(R.id.spnThanhVienUpdate);
        Spinner spnSach = dialog.findViewById(R.id.spnSachUpdate);
        EditText edtngay = dialog.findViewById(R.id.edtngatThueUpdate);
        CheckBox chk = dialog.findViewById(R.id.chkcheck);
        Button btnsubmit = dialog.findViewById(R.id.btnsubmitUpdatePM);
        Button btncancle = dialog.findViewById(R.id.btncanUpdatePM);

        edtngay.setText(phieuMuon.getNgay());
        getDataThanhVien(spnTV);
        getDataSach(spnSach);

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtngay.getText().toString().isEmpty() ) {
                    Toast.makeText(context, "Bạn không được để trống!!!", Toast.LENGTH_SHORT).show();
                } else {
                    int status;
                    HashMap<String, Object> hsTV = (HashMap<String, Object>) spnTV.getSelectedItem();
                    int matv = (int) hsTV.get("maTV");
                    HashMap<String, Object> hsSach = (HashMap<String, Object>) spnSach.getSelectedItem();
                    int masach = (int) hsSach.get("maSach");
                    int id = phieuMuon.getMaPM();
                    String ngaythue = edtngay.getText().toString();

                    if(chk.isChecked()){
                        status = 1;
                    }else{
                        status = 0;
                    }

                    boolean check = dao.updatep(id,matv,masach,status, ngaythue);
                    if(check){
                        loadData();
                        Toast.makeText(context, "Update mã phiếu thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else{
                        Toast.makeText(context, "Update mã phiếu không thành công", Toast.LENGTH_SHORT).show();
                    }
//

                }
            }

        });
        btncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void getDataThanhVien(Spinner spnThanhVien){
        ThanhVienDao thanhVienDao = new ThanhVienDao(context);
        List<ThanhVien> list = thanhVienDao.getAll();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for(ThanhVien tv : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maTV",tv.getMaTV());
            hs.put("hoTen",tv.getHoTenTV());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"hoTen"},
                new int[]{android.R.id.text1});
        spnThanhVien.setAdapter(simpleAdapter);
    }

    private void getDataSach(Spinner spnSach){
        SachDao sachDao = new SachDao(context);
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
                context,
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tenSach"},
                new int[]{android.R.id.text1});
        spnSach.setAdapter(simpleAdapter);
    }

    private void loadData () {
        list.clear();
        list = dao.getAll();
        notifyDataSetChanged();
    }
}
