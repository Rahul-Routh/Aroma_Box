package com.purpulingo.aromabox.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.purpulingo.aromabox.Model.PopUpFarmerListModel;
import com.purpulingo.aromabox.R;

import java.util.List;

public class PopUpFarmerListAdapter extends ArrayAdapter<PopUpFarmerListModel> {
    Context context;
    List<PopUpFarmerListModel> arrayListData;

    public PopUpFarmerListAdapter(@NonNull Context context, @NonNull List<PopUpFarmerListModel> arrayListData) {
        super(context, R.layout.pop_calender_farmer_list_view, arrayListData);
        this.arrayListData = arrayListData;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pop_calender_farmer_list_view, null, true);

        TextView farmerId, farmerName, farmerVillage;

        farmerName = (TextView) view.findViewById(R.id.farmerName);
        farmerId = (TextView) view.findViewById(R.id.farmerId);
        farmerVillage = (TextView) view.findViewById(R.id.village_name);

        farmerName.setText(arrayListData.get(position).getFarmerName());
        farmerId.setText(" ("+arrayListData.get(position).getFarmerId()+")");
        farmerVillage.setText(arrayListData.get(position).getFarmerVillage());

        return view;
    }
}
