package com.purpulingo.aromabox.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.purpulingo.aromabox.Model.ExitingBatchListModel;
import com.purpulingo.aromabox.Model.FarmerListModel;
import com.purpulingo.aromabox.R;

import java.util.List;

public class FarmerListAdapter extends ArrayAdapter<FarmerListModel> {
    Context context;
    List<FarmerListModel> arrayListData;

    public FarmerListAdapter(@NonNull Context context, @NonNull List<FarmerListModel> arrayListData) {
        super(context, R.layout.farmer_list_view, arrayListData);
        this.arrayListData = arrayListData;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.farmer_list_view, null, true);

        TextView farmerId, farmerName, farmerDate, farmerBioQuantity, farmerBioQuantityPrice;

       // paymentDetails = (LinearLayout) view.findViewById(R.id.paymentDetails);

        farmerId = (TextView) view.findViewById(R.id.farmerIdList);
        farmerName = (TextView) view.findViewById(R.id.farmerName_list);
        farmerDate = (TextView) view.findViewById(R.id.farmerDateList);
        farmerBioQuantity = (TextView) view.findViewById(R.id.farmerQuantityTotal);
        farmerBioQuantityPrice = (TextView) view.findViewById(R.id.farmerQuantityTotalPrice);
        //paymentStatus = (TextView) view.findViewById(R.id.paymentStatus);


        farmerId.setText(arrayListData.get(position).getFarmerId());
        farmerName.setText(arrayListData.get(position).getFarmerName());
        farmerDate.setText(arrayListData.get(position).getFarmerDate());
        farmerBioQuantity.setText(arrayListData.get(position).getFarmerBioQuantity());
        farmerBioQuantityPrice.setText(arrayListData.get(position).getFarmerBioQuantityPrice());
       // paymentStatus.setBackgroundColor(Color.parseColor("#F44336"));
          //  paymentStatus.setText("Unpaid")



        return view;
    }
}
