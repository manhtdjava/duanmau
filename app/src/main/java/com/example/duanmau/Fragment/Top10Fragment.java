package com.example.duanmau.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.Adapter.TopAdapter;
import com.example.duanmau.Database.PhieuMuonDao;
import com.example.duanmau.Model.Top;
import com.example.duanmau.R;

import java.util.List;

public class Top10Fragment extends Fragment {
    List<Top> list;
    RecyclerView rcv;
    TopAdapter adapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top10, container, false);
        rcv = view.findViewById(R.id.rcvtop10);
        PhieuMuonDao phieuMuonDao = new PhieuMuonDao(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);

        list = (List<Top>) phieuMuonDao.getTop();
        adapter = new TopAdapter(getActivity(), list);
        rcv.setAdapter(adapter);
        return view;
    }
}