package com.purpulingo.aromabox.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.purpulingo.aromabox.Model.FarmerDueListModel;
import com.purpulingo.aromabox.Model.FarmerPaidListModel;
import com.purpulingo.aromabox.R;

import java.util.List;

public class FarmerDueListAdapter extends ArrayAdapter<FarmerDueListModel> {
    Context context;
    List<FarmerDueListModel> arrayListData;

    public FarmerDueListAdapter(@NonNull Context context, @NonNull List<FarmerDueListModel> arrayListData) {
        super(context, R.layout.farmer_due_list_view, arrayListData);
        this.arrayListData = arrayListData;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.farmer_due_list_view, null, true);

        TextView farmerId, farmerName, dueAmount;

        farmerName = (TextView) view.findViewById(R.id.farmerNameDueList);
        farmerId = (TextView) view.findViewById(R.id.farmerIdDueList);
        dueAmount = (TextView) view.findViewById(R.id.paymentAmountDueList);

        farmerName.setText(arrayListData.get(position).getFarmerName());
        farmerId.setText(arrayListData.get(position).getFarmerId());
        dueAmount.setText(arrayListData.get(position).getDueAmount());

        return view;
    }
}
