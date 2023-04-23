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

import com.purpulingo.aromabox.Activity.NotificationFarmerActivity;
import com.purpulingo.aromabox.Model.NotificationListModel;
import com.purpulingo.aromabox.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationListAdapter extends ArrayAdapter<NotificationListModel> {

    Context context;
    List<NotificationListModel> arrayListData;


    public NotificationListAdapter(@NonNull Context context, List<NotificationListModel> arrayListData ) {
        super(context, R.layout.notification_list_view, arrayListData);
        this.arrayListData = arrayListData;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list_view,null,true);

        TextView notification_number, notification_date,cultivation_area, farmerCount;
        notification_number =(TextView) view.findViewById(R.id.notification_number);
        notification_date =(TextView) view.findViewById(R.id.notification_date);
        cultivation_area =(TextView) view.findViewById(R.id.cultivation_area);
        farmerCount =(TextView) view.findViewById(R.id.farmerCount);

        notification_number.setText(arrayListData.get(position).getNotificationNo());
        notification_date.setText(arrayListData.get(position).getNotificationDate());
        cultivation_area.setText(arrayListData.get(position).getCultivationArea());
        farmerCount.setText(arrayListData.get(position).getFarmerCount());

        return view;
    }
}
