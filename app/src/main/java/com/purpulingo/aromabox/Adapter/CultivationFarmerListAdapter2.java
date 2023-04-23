package com.purpulingo.aromabox.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.purpulingo.aromabox.Model.CultivationFarmerListModel;
import com.purpulingo.aromabox.Model.CultivationFarmerListModel2;
import com.purpulingo.aromabox.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CultivationFarmerListAdapter2 extends ArrayAdapter<CultivationFarmerListModel2> {
    Context context;
    List<CultivationFarmerListModel2> arrayListData;


    public CultivationFarmerListAdapter2(@NonNull Context context, @NonNull List<CultivationFarmerListModel2> arrayListData) {
        super(context, R.layout.cultivation_farmer_list_view2, arrayListData);
        this.arrayListData = arrayListData;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cultivation_farmer_list_view2, null, true);

        TextView farmerId, farmerName, farmerVillage, farmerArea, lastCutting, nextCutting, lastIrrigation, lastFertigation;
        CircleImageView farmerImg;

        farmerName = (TextView) view.findViewById(R.id.farmerNameCultivation);
        farmerId = (TextView) view.findViewById(R.id.farmerIdCultivation);
        //farmerVillage = (TextView) view.findViewById(R.id.farmerAddress);
        farmerImg = (CircleImageView) view.findViewById(R.id.farmerImg);
        farmerArea = (TextView) view.findViewById(R.id.farmerArea);
        lastCutting = (TextView) view.findViewById(R.id.lastCutting);
        nextCutting = (TextView) view.findViewById(R.id.nextCutting);
        lastIrrigation = (TextView) view.findViewById(R.id.lastIrrigation);
        lastFertigation = (TextView) view.findViewById(R.id.lastFertigation);

        farmerName.setText(arrayListData.get(position).getFarmerName());
        farmerId.setText(" ("+arrayListData.get(position).getFarmerId()+")");
        //farmerVillage.setText(arrayListData.get(position).getFarmerVillage());
        Glide.with(context).load(arrayListData.get(position).getFarmerImg()).into(farmerImg);
        farmerArea.setText(arrayListData.get(position).getFarmerArea() + " dcml");
        lastCutting.setText(arrayListData.get(position).getLastCutting());
        nextCutting.setText(arrayListData.get(position).getNextCutting());
        lastIrrigation.setText(arrayListData.get(position).getLastIrrigation());
        lastFertigation.setText(arrayListData.get(position).getLastFertigation());
       // farmerImg.setText(arrayListData.get(position).getFarmerImg());

        return view;
    }
}
