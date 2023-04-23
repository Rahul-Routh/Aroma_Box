package com.purpulingo.aromabox.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.purpulingo.aromabox.Model.ExitingBatchListModel;
import com.purpulingo.aromabox.Model.NotificationListModel;
import com.purpulingo.aromabox.R;

import java.util.List;

public class ExitingListAdapter extends ArrayAdapter<ExitingBatchListModel> {
    Context context;
    List<ExitingBatchListModel> arrayListData;

    public ExitingListAdapter(@NonNull Context context, @NonNull List<ExitingBatchListModel> arrayListData) {
        super(context, R.layout.exiting_batch_list_view, arrayListData);
        this.arrayListData = arrayListData;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exiting_batch_list_view, null, true);

        TextView machineId, startDate, startTime, quantity, batchId, qualityGrade;

        //machineId = (TextView) view.findViewById(R.id.pos_id);
        startDate = (TextView) view.findViewById(R.id.start_date_list);
        startTime = (TextView) view.findViewById(R.id.start_time_list);
        quantity = (TextView) view.findViewById(R.id.biomassExitingListQuantity);
        batchId = (TextView) view.findViewById(R.id.batchId_list);
        qualityGrade = (TextView) view.findViewById(R.id.qualityGrade);


        //machineId.setText(arrayListData.get(position).getMachineId());
        startDate.setText(arrayListData.get(position).getStartDate());
        startTime.setText(arrayListData.get(position).getStartTime());
        quantity.setText(arrayListData.get(position).getQuantity()+" ton");
        batchId.setText(arrayListData.get(position).getBatchId());
        qualityGrade.setText(arrayListData.get(position).getGrade());

        return view;
    }
}
