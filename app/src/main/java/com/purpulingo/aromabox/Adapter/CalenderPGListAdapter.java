package com.purpulingo.aromabox.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.purpulingo.aromabox.Model.CalenderPGListModel;
import com.purpulingo.aromabox.R;

import java.util.List;

public class CalenderPGListAdapter extends ArrayAdapter<CalenderPGListModel> {
    Context context;
    List<CalenderPGListModel> arrayListData;

    public CalenderPGListAdapter(@NonNull Context context, @NonNull List<CalenderPGListModel> arrayListData) {
        super(context, R.layout.calender_pg_list_view, arrayListData);
        this.arrayListData = arrayListData;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calender_pg_list_view, null, true);

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
