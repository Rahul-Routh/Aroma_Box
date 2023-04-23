package com.purpulingo.aromabox.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.purpulingo.aromabox.Model.HarvestingListModel;
import com.purpulingo.aromabox.R;

import java.util.List;

public class HarvestingListAdapter extends ArrayAdapter<HarvestingListModel> {
    Context context;
    List<HarvestingListModel> arrayListData;


    public HarvestingListAdapter(@NonNull Context context, @NonNull List<HarvestingListModel> arrayListData) {
        super(context, R.layout.harvesting_list_view, arrayListData);
        this.arrayListData = arrayListData;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.harvesting_list_view, null, true);

        TextView year, month, totalArea, areaUnite;

        year = (TextView) view.findViewById(R.id.year);
        month = (TextView) view.findViewById(R.id.month);
        totalArea = (TextView) view.findViewById(R.id.totalArea);
        areaUnite = (TextView) view.findViewById(R.id.areaUnite);

        year.setText(arrayListData.get(position).getYear());
        month.setText(arrayListData.get(position).getMonth());
        totalArea.setText(arrayListData.get(position).getTotalArea());
        areaUnite.setText(arrayListData.get(position).getAreaUnite());

        return view;
    }
}
