package com.purpulingo.aromabox.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.purpulingo.aromabox.Global.UserSessionManager;
import com.purpulingo.aromabox.Model.CultivationFarmerListModel2;
import com.purpulingo.aromabox.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CalenderFarmerListAdapter extends ArrayAdapter<CalenderFarmerListModel> {
    Context context;
    List<CalenderFarmerListModel> arrayListData;
    UserSessionManager userSessionManager;
    private CheckBoxCheckedListener checkedListener, RemovedListener;

    public CalenderFarmerListAdapter(@NonNull Context context, @NonNull List<CalenderFarmerListModel> arrayListData) {
        super(context, R.layout.calender_farmer_list_view, arrayListData);
        this.arrayListData = arrayListData;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calender_farmer_list_view, null, true);

        TextView farmerId, farmerName, farmerVillage, farmerArea, lastCutting, nextCutting, lastIrrigation, lastFertigation;
        CircleImageView farmerImg;
        CheckBox checkbox;

        userSessionManager = new UserSessionManager(getContext());

        farmerName = (TextView) view.findViewById(R.id.farmerNameCultivation);
        farmerId = (TextView) view.findViewById(R.id.farmerIdCultivation);
        //farmerVillage = (TextView) view.findViewById(R.id.farmerAddress);
        farmerImg = (CircleImageView) view.findViewById(R.id.farmerImg);
        farmerArea = (TextView) view.findViewById(R.id.farmerArea);
        checkbox = (CheckBox) view.findViewById(R.id.checkbox2);
        lastCutting = (TextView) view.findViewById(R.id.lastCutting);
        nextCutting = (TextView) view.findViewById(R.id.nextCutting);
        lastIrrigation = (TextView) view.findViewById(R.id.lastIrrigation);
        lastFertigation = (TextView) view.findViewById(R.id.lastFertigation);


        // farmerImg.setText(arrayListData.get(position).getFarmerImg());

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkbox.isChecked()) {
                    checkedListener.getCheckBoxChecked(position);
                } else {
                    RemovedListener.getCheckBoxRemoved(position);
                }
//                if(checkedListener != null){
//                    checkedListener.getCheckBoxChecked(position);
//                }
            }
        });

        farmerName.setText(arrayListData.get(position).getFarmerName());
        farmerId.setText(" ("+arrayListData.get(position).getFarmerId()+")");
        //farmerVillage.setText(arrayListData.get(position).getFarmerVillage());
        Glide.with(context).load(arrayListData.get(position).getFarmerImg()).into(farmerImg);
        farmerArea.setText(arrayListData.get(position).getFarmerArea() +" dcml");
        lastCutting.setText(arrayListData.get(position).getLastCutting());
        nextCutting.setText(arrayListData.get(position).getNextCutting());
        lastIrrigation.setText(arrayListData.get(position).getLastIrrigation());
        lastFertigation.setText(arrayListData.get(position).getLastFertigation());

        return view;
    }

    public interface CheckBoxCheckedListener {
        void getCheckBoxChecked(int position);
        void getCheckBoxRemoved(int position);
    }

    public void setCheckedListener(CheckBoxCheckedListener checkedListener) {
        this.checkedListener = checkedListener;
        this.RemovedListener = checkedListener;
    }
}
