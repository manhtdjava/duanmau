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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.Database.LoaiSachDao;
import com.example.duanmau.Database.SachDao;
import com.example.duanmau.Model.LoaiSach;
import com.example.duanmau.Model.Sach;
import com.example.duanmau.Model.ThanhVien;
import com.example.duanmau.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class SachApdater extends RecyclerView.Adapter<SachApdater.SachViewHolder> implements Filterable {
    Context context;
    List<Sach> list;
    List<Sach> listOld;
    SachDao sachDao;
    private ArrayList<HashMap<String, Object>> listHM;

    public SachApdater(Context context, List<Sach> list, ArrayList<HashMap<String, Object>> listHM) {
        this.context = context;
        this.list = list;
        this.listOld = list;
        sachDao = new SachDao(context);
        this.listHM = listHM;
    }

    @NonNull
    @Override
    public SachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_sach, parent, false);
        return new SachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachViewHolder holder, int position) {
        Sach sach = list.get(position);
        LoaiSachDao loaiSachDao = new LoaiSachDao(context);
        LoaiSach loaiSach = loaiSachDao.getID(String.valueOf(sach.getMaLoai()));

        holder.txtma.setText("Mã sách: " + sach.getmSach());
        holder.txtten.setText("Tên sách: " + sach.getTenSach());
        holder.txtgiaThue.setText("Giá thuê: " + sach.getGiaThue());
        holder.txtnxb.setText("NXB: " + sach.getNxb());
        holder.txtloaiSach.setText("Loại sách: " + loaiSach.getTenLoai());

        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa Sách");
                builder.setMessage("Bạn có chắc muốn xóa sách này chứ ?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int check = sachDao.delete(list.get(holder.getAdapterPosition()).getmSach());
                        switch (check) {
                            case 1:
                                loadData();
                                Toast.makeText(context, "Xóa thành công sách", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context, "Xóa không thành công sách", Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(context, "Không xóa được sách này vì đang còn tồn tại trong phiếu mượn", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Hủy", null);
                builder.create().show();
            }
        });
        holder.itemSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogShowUpdate(sach);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String sert = charSequence.toString();
                if (sert.isEmpty()){
                    list = listOld;
                }else {
                    List<Sach> list1 = new ArrayList<>();
                    for (Sach sach : listOld){
                        if (sach.getTenSach().toLowerCase().contains(sert.toLowerCase())){
                            list1.add(sach);
                        }
                    }
                    list = list1;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (List<Sach>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class SachViewHolder extends RecyclerView.ViewHolder {
        TextView txtma, txtten, txtgiaThue, txtloaiSach, txtnxb;
        ImageButton imgdelete;
        CardView itemSach;

        public SachViewHolder(@NonNull View itemView) {
            super(itemView);
            txtma = itemView.findViewById(R.id.txtmaSach);
            txtten = itemView.findViewById(R.id.txttenSach);
            txtgiaThue = itemView.findViewById(R.id.txtgiaThue);
            txtloaiSach = itemView.findViewById(R.id.txtloaiSach);
            itemSach = itemView.findViewById(R.id.itemSach);
            imgdelete = itemView.findViewById(R.id.imgDeleteSach);
            txtnxb = itemView.findViewById(R.id.txtnxb);
        }
    }
//    public void sort(){
//        Collections.sort(list, new Comparator<Sach>() {
//            @Override
//            public int compare(Sach o1, Sach o2) {
//                if (o1.getGiaThue() > o2.getGiaThue()){
//                    Toast.makeText(context, "Đã sắp xếp tăng dần", Toast.LENGTH_SHORT).show();
//                    notifyDataSetChanged();
//                    return 1;
//                }else {
//                    if (o1.getGiaThue() == o2.getGiaThue()){
//                        return 0;
//                    }else return -1;
//                }
//            }
//        });
//    }
//    public void sort2(){
//        Collections.sort(list, new Comparator<Sach>() {
//            @Override
//            public int compare(Sach o1, Sach o2) {
//                if (o1.getGiaThue() < o2.getGiaThue()){
//                    Toast.makeText(context, "Đã sắp xếp giảm dần", Toast.LENGTH_SHORT).show();
//                    notifyDataSetChanged();
//                    return 1;
//                }else {
//                    if (o1.getGiaThue() == o2.getGiaThue()){
//                        return 0;
//                    }else return -1;
//                }
//            }
//        });
//    }
    public void sort(){
        Collections.sort(list, new Comparator<Sach>() {
            @Override
            public int compare(Sach o1, Sach o2) {
                String[] n1, n2;
                n1 = o1.getTenSach().split("");
                n2= o2.getTenSach().split("");
                String s1 = n1[n1.length-1];
                String s2= n2[n2.length-1];
                if (s1.codePointAt(0) > s2.codePointAt(0)){
                    Toast.makeText(context, "Đã sắp xếp tăng dần", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    return 1;
                }
                else return -1;

            }
        });
}
    public void sort2(){
        Collections.sort(list, new Comparator<Sach>() {
            @Override
            public int compare(Sach o1, Sach o2) {
                String[] n1, n2;
                n1 = o1.getTenSach().split("");
                n2= o2.getTenSach().split("");
                String s1 = n1[n1.length-1];
                String s2= n2[n2.length-1];
                if (s1.codePointAt(0) < s2.codePointAt(0)){
                    Toast.makeText(context, "Đã sắp xếp giảm dần", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    return 1;
                }
                else return -1;

            }
        });
    }


    public void dialogShowUpdate(Sach sach) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_updatesach);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText edtten = dialog.findViewById(R.id.edttenSachUpdate);
        EditText edtgiathue = dialog.findViewById(R.id.edtGiaThueUpdate);
        EditText edtnxb = dialog.findViewById(R.id.edtnxbupdate);
        Spinner spnSach = dialog.findViewById(R.id.spnSachupdate);
        Button btnsubmit = dialog.findViewById(R.id.btnsubmiupdatetSach);
        Button btncancle = dialog.findViewById(R.id.btncanupdateSach);

        edtten.setText(sach.getTenSach());
        edtgiathue.setText(String.valueOf(sach.getGiaThue()));
        edtnxb.setText(String.valueOf(sach.getNxb()));

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listHM,
                (android.R.layout.simple_list_item_1),
                new String[]{"tenLoai"},
                new int[]{android.R.id.text1}
        );
        spnSach.setAdapter(simpleAdapter);
        int index = 0;
        int position = -1;

        for (HashMap<String, Object> item : listHM) {
            if ((int) item.get("maLoai") == sach.getMaLoai()) {
                position = index;
            }
            index++;
        }
        spnSach.setSelection(position);

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtten.getText().toString().isEmpty() || edtgiathue.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Bạn không được để trống!!!", Toast.LENGTH_SHORT).show();
                } else {
                    String hoten = edtten.getText().toString();
                    String giathue = edtgiathue.getText().toString();
                    String nxb = edtnxb.getText().toString();
                    HashMap<String, Object> hs = (HashMap<String, Object>) spnSach.getSelectedItem();
                    int maloai = (int) hs.get("maLoai");
                    int tien = Integer.parseInt(giathue);
                    boolean check = sachDao.update(sach.getmSach(), hoten, tien, Integer.parseInt(nxb),maloai);
                    if (check) {
                        loadData();
                        Toast.makeText(context, "Cập nhật thành công sách", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(context, "Cập nhật không thành công sách", Toast.LENGTH_SHORT).show();
                    }

                }
            }

        });
        btncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtten.setText("");
                edtgiathue.setText("");
                edtnxb.setText("");
            }
        });
        dialog.show();
    }

        private void loadData () {
            list.clear();
            list = sachDao.getAll();
            notifyDataSetChanged();
        }
    }

