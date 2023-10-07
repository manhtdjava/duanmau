package com.example.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.Database.PhieuMuonDao;
import com.example.duanmau.Model.Top;
import com.example.duanmau.R;

import java.util.List;

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.TopViewHolder>{
    Context context;
    List<Top> list;


    public TopAdapter(Context context, List<Top> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public TopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_top, parent, false);
        return new TopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopViewHolder holder, int position) {
            Top top = list.get(position);
             holder.txtma.setText(String.valueOf(holder.getAdapterPosition() + 1));
            holder.txtten.setText(top.getTenSach());
            holder.txtsoluong.setText(String.valueOf(top.getSoLuong()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TopViewHolder extends RecyclerView.ViewHolder {
        TextView txtma, txtten, txtsoluong;

        public TopViewHolder(@NonNull View itemView) {
            super(itemView);
            txtma = itemView.findViewById(R.id.txtmaTop);
            txtten = itemView.findViewById(R.id.tenSachTop);
            txtsoluong = itemView.findViewById(R.id.soluong);
        }
    }
}
