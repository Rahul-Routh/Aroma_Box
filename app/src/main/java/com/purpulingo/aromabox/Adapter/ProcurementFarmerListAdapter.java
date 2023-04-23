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
import com.purpulingo.aromabox.Model.ProcurementFarmerListModel;
import com.purpulingo.aromabox.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProcurementFarmerListAdapter extends ArrayAdapter<ProcurementFarmerListModel> {

    Context context;
    List<ProcurementFarmerListModel> farmerArrayListData;


    public ProcurementFarmerListAdapter(@NonNull Context context, List<ProcurementFarmerListModel> farmerArrayListData){
        super(context, R.layout.procurement_farmer_list_view, farmerArrayListData);
        this.farmerArrayListData = farmerArrayListData;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.procurement_farmer_list_view, null, true);

        CircleImageView farmerImage;
        TextView farmerId, farmerName, cultivation_area_farmer, lastCutting, village_name;


        farmerImage = (CircleImageView) view.findViewById(R.id.farmerImage);
        farmerId = (TextView) view.findViewById(R.id.farmerId);
        farmerName = (TextView) view.findViewById(R.id.farmerName);
        lastCutting = (TextView) view.findViewById(R.id.last_Cutting);
        cultivation_area_farmer = (TextView) view.findViewById(R.id.cultivation_area_farmer);
        village_name = (TextView) view.findViewById(R.id.village_name);

        Glide.with(context).load(farmerArrayListData.get(position).getFarmerPicture()).into(farmerImage);
        //Picasso.get().load(farmerArrayListData.get(position).getFarmerPicture()).into(farmerImage);

//        farmerId.setText(farmerArrayListData.get(position).getFarmerID());
//        farmerName.setText(farmerArrayListData.get(position).getFarmerName());
//        lastCutting.setText(farmerArrayListData.get(position).getLastCutting());
//        cultivation_area_farmer.setText(farmerArrayListData.get(position).getCultivationArea()+ " dcml");
//        village_name.setText(farmerArrayListData.get(position).getVillage_name());

        farmerId.setText(" ("+farmerArrayListData.get(position).getFarmerID()+")");
        farmerName.setText(farmerArrayListData.get(position).getFarmerName());
        lastCutting.setText(farmerArrayListData.get(position).getLastCutting());
        cultivation_area_farmer.setText(farmerArrayListData.get(position).getCultivationArea()+ " dcml");
        village_name.setText(farmerArrayListData.get(position).getVillage_name());

        return view;
    }
}
