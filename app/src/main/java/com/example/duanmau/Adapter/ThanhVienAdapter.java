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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.Database.ThanhVienDao;
import com.example.duanmau.Model.ThanhVien;
import com.example.duanmau.R;

import java.util.List;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ThanhVienViewHolder>{
    private Context context;
    private List<ThanhVien> list;
    ThanhVienDao thanhVienDao;

    public ThanhVienAdapter(Context context, List<ThanhVien> list) {
        this.context = context;
        this.list = list;
        thanhVienDao = new ThanhVienDao(context);
    }

    @NonNull
    @Override
    public ThanhVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_thanhvien, parent, false);
        return new ThanhVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienViewHolder holder, int position) {
        ThanhVien thanhVien = list.get(position);
        holder.txtmaTV1.setText("Mã TV: "+ thanhVien.getMaTV());
        holder.txttenTV1.setText("Tên TV: "+thanhVien.getHoTenTV());
        holder.txtnamSinh1.setText("Năm sinh: "+thanhVien.getNamSinh());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    dialogShowUpdate(thanhVien);
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Bạn chắc chắn muốn xóa thành viên này chứ");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int check = thanhVienDao.delete(list.get(holder.getAdapterPosition()).getMaTV());
                        switch (check){
                            case 1:
                                loadData();
                                Toast.makeText(context, "Xóa thành viên thành công", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context, "Xóa thành viên thất bại", Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(context, "Thành viên đang tồn tại phiếu mượn, hiện tại không thể xóa", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Hủy",null);
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ThanhVienViewHolder extends RecyclerView.ViewHolder {
        TextView txtmaTV1, txttenTV1, txtnamSinh1;
        ImageButton imgDelete;
        CardView card;
        public ThanhVienViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmaTV1 = itemView.findViewById(R.id.txtmaTV);
            txttenTV1 = itemView.findViewById(R.id.txttenTV);
            txtnamSinh1 = itemView.findViewById(R.id.txtnamSinhTV);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            card = itemView.findViewById(R.id.itemTV);
        }
    }
    public void dialogShowUpdate(ThanhVien tv){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_update);
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText edtten = dialog.findViewById(R.id.edttenUpdate);
        EditText edtnamSinh = dialog.findViewById(R.id.edtnamSinhUpdate);
        Button btnsubmit = dialog.findViewById(R.id.btnsubmitupdate);
        Button btncancle = dialog.findViewById(R.id.btncanupdate);

        edtten.setText(tv.getHoTenTV());
        edtnamSinh.setText(tv.getNamSinh());

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtten.getText().toString().isEmpty() || edtnamSinh.getText().toString().isEmpty() ) {
                    Toast.makeText(context, "Bạn không được để trống!!!", Toast.LENGTH_SHORT).show();
                } else {
                    String hoten = edtten.getText().toString();
                    String namsinh = edtnamSinh.getText().toString();
                    int id = tv.getMaTV();
                    boolean check  = thanhVienDao.update(id, hoten,namsinh);
                    if(check){
                        loadData();
                        Toast.makeText(context, "Cập nhật nhân viên thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else{
                        Toast.makeText(context, "Cập nhật nhân viên thất bại", Toast.LENGTH_SHORT).show();
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
    private void loadData(){
        list.clear();
        list = thanhVienDao.getDSThanhVien();
        notifyDataSetChanged();
    }

}
