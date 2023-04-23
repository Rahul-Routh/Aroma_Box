package com.purpulingo.aromabox.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.purpulingo.aromabox.Model.OilListModel;
import com.purpulingo.aromabox.R;

import java.util.List;

public class OilListAdapter extends ArrayAdapter<OilListModel> {
    Context context;
    List<OilListModel> arrayListData;


    public OilListAdapter(@NonNull Context context,  List<OilListModel> arrayListData) {
        super(context, R.layout.oil_list_view, arrayListData);
        this.arrayListData = arrayListData;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oil_list_view,null,true);
        TextView oilDate, oilQuantity;

        oilDate =(TextView) view.findViewById(R.id.oilDate);
        oilQuantity =(TextView) view.findViewById(R.id.oilQuantity);

        oilDate.setText(arrayListData.get(position).getDate());
        oilQuantity.setText(arrayListData.get(position).getOilQuantity());

        return view;
    }
}
