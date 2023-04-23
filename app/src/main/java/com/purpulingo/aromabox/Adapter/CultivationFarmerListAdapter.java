package com.purpulingo.aromabox.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.purpulingo.aromabox.Activity.CultivationFarmerProfileActivity;
import com.purpulingo.aromabox.Activity.NotificationFarmerActivity;
import com.purpulingo.aromabox.Model.CultivationFarmerListModel;
import com.purpulingo.aromabox.R;

import java.util.ArrayList;
import java.util.List;

public class CultivationFarmerListAdapter extends ArrayAdapter<CultivationFarmerListModel> {
    Context context;
    List<CultivationFarmerListModel> arrayListData;

    public CultivationFarmerListAdapter(@NonNull Context context, @NonNull List<CultivationFarmerListModel> arrayListData) {
        super(context, R.layout.cultivation_farmer_list_view, arrayListData);
        this.arrayListData = arrayListData;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cultivation_farmer_list_view, null, true);

        TextView pgName, pgId, pgCount, pgTotalArea;

        pgName = (TextView) view.findViewById(R.id.pgName);
        //pgId = (TextView) view.findViewById(R.id.pgId);
        pgCount = (TextView) view.findViewById(R.id.pgCount);
        pgTotalArea = (TextView) view.findViewById(R.id.pgTotalArea);

        pgName.setText(arrayListData.get(position).getPgName());
        //pgId.setText(arrayListData.get(position).getPgId());
        pgCount.setText(arrayListData.get(position).getPgCount());
        pgTotalArea.setText(arrayListData.get(position).getPgArea());

        return view;
    }
}
