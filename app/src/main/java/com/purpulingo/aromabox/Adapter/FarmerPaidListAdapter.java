package com.purpulingo.aromabox.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.purpulingo.aromabox.Model.FarmerPaidListModel;
import com.purpulingo.aromabox.R;

import java.util.List;

public class FarmerPaidListAdapter extends ArrayAdapter<FarmerPaidListModel> {
    Context context;
    List<FarmerPaidListModel> arrayListData;

    public FarmerPaidListAdapter(@NonNull Context context, @NonNull List<FarmerPaidListModel> arrayListData) {
        super(context, R.layout.farmer_paid_list_view, arrayListData);
        this.arrayListData = arrayListData;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.farmer_paid_list_view, null, true);
        TextView farmerId, farmerName, paymentDate, paymentAmount;

        farmerName = (TextView) view.findViewById(R.id.farmerNamePaidList);
        farmerId = (TextView) view.findViewById(R.id.farmerIdPaidList);
        paymentDate = (TextView) view.findViewById(R.id.datePaidList);
        paymentAmount = (TextView) view.findViewById(R.id.paymentAmountPaidList);

        farmerName.setText(arrayListData.get(position).getFarmerName());
        farmerId.setText(arrayListData.get(position).getFarmerId());
        paymentDate.setText(arrayListData.get(position).getPaymentDate());
        paymentAmount.setText(arrayListData.get(position).getPaymentAmount());

        return view;
    }
}
