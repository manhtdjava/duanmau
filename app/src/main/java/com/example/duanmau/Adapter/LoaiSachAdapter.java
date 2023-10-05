package com.example.duanmau.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.example.duanmau.Database.LoaiSachDao;
import com.example.duanmau.Model.LoaiSach;
import com.example.duanmau.R;

import java.util.List;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.LoaiSachViewHolder>{

    Context context;
    private List<LoaiSach> list;
    LoaiSachDao dao;

    public LoaiSachAdapter(Context context, List<LoaiSach> list) {
        this.context = context;
        this.list = list;
        dao = new LoaiSachDao(context);
    }

    @NonNull
    @Override
    public LoaiSachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_loai_sach, parent,false);
        return new LoaiSachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSachViewHolder holder, int position) {
        LoaiSach loaiSach = list.get(position);
        holder.txtma.setText("Mã loại sách: "+ loaiSach.getMaLoai());
        holder.txtten.setText("Tên loại sách: "+ loaiSach.getTenLoai());
        holder.itemLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogShowUpdate(loaiSach);
            }
        });
        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa loại sách");
                builder.setMessage("Bạn có muốn xóa loại sách này không ?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoaiSachDao dao = new LoaiSachDao(context);
                        int check = dao.deleteLS(list.get(holder.getAdapterPosition()).getMaLoai());
                        switch (check){
                            case 1:
                                list.clear();
                                list = dao.getAll();
                                notifyDataSetChanged();
                                Toast.makeText(context, "Xóa loại sách thành công", Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(context, "Không thể xóa vì đang có sách thuộc thể loại", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context, "Xóa loại sách không thành công", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Cancel",null);
                builder.create().show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LoaiSachViewHolder extends RecyclerView.ViewHolder {
        TextView txtten, txtma;
        ImageButton imgdelete;
        CardView itemLoaiSach;
        public LoaiSachViewHolder(@NonNull View itemView) {
            super(itemView);
            txtma = itemView.findViewById(R.id.txtmaLoaiSach);
            txtten = itemView.findViewById(R.id.txttenLoaiSach);
            imgdelete = itemView.findViewById(R.id.imgDeleteLoaiSach);
            itemLoaiSach = itemView.findViewById(R.id.itemLoaiSach);
        }
    }
    public void dialogShowUpdate(LoaiSach tv){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_updateloaisach);
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText edtten = dialog.findViewById(R.id.edttenLoaiSachUpdate);
        Button btnsubmit = dialog.findViewById(R.id.btnsubmiupdatetLoaiSach);
        Button btncancle = dialog.findViewById(R.id.btncanupdateLoaiSach);

        edtten.setText(tv.getTenLoai());

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtten.getText().toString().isEmpty()  ) {
                    Toast.makeText(context, "Bạn không được để trống!!!", Toast.LENGTH_SHORT).show();
                } else {
                    String ten = edtten.getText().toString();
                    boolean check = dao.update(tv.getMaLoai(), ten);
                    if (check) {
                        loadData();
                        Toast.makeText(context, "Cập nhật thành công loại sách", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(context, "Cập nhật không thành công loại sách", Toast.LENGTH_SHORT).show();
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
    private void loadData () {
        list.clear();
        list = dao.getAll();
        notifyDataSetChanged();
    }
}
