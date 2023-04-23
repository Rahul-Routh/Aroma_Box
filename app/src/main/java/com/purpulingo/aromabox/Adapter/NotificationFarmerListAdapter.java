package com.purpulingo.aromabox.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.purpulingo.aromabox.Global.UserSessionManager;
import com.purpulingo.aromabox.Model.NotificationFarmerListModel;
import com.purpulingo.aromabox.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationFarmerListAdapter extends ArrayAdapter<NotificationFarmerListModel> {

    Context context;
    List<NotificationFarmerListModel> farmerArrayListData;

    UserSessionManager userSessionManager;

    private CheckBoxCheckedListener checkedListener, RemovedListener;
   // private CheckBoxRemovedListener RemovedListener;


    public NotificationFarmerListAdapter(@NonNull Context context, List<NotificationFarmerListModel> farmerArrayListData){
        super(context, R.layout.notification_farmer_list_view, farmerArrayListData);
        this.farmerArrayListData = farmerArrayListData;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_farmer_list_view,null, true);

        CircleImageView farmerImage;
        TextView farmerId, farmerName, cultivation_area_farmer, totalCultivationAreaTextView, lastCutting;
        CheckBox checkbox;
        Button totalCultivationSelected;


        farmerImage = (CircleImageView) view.findViewById(R.id.farmerImage);
        farmerId = (TextView) view.findViewById(R.id.farmerId);
        farmerName = (TextView) view.findViewById(R.id.farmerName);
        lastCutting = (TextView) view.findViewById(R.id.last_Cutting);
        cultivation_area_farmer = (TextView) view.findViewById(R.id.cultivation_area_farmer);
        checkbox = (CheckBox) view.findViewById(R.id.checkbox);

        userSessionManager = new UserSessionManager(getContext());


        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkbox.isChecked()){
                    checkedListener.getCheckBoxChecked(position);
                }else {
                    RemovedListener.getCheckBoxRemoved(position);
                }
//                if(checkedListener != null){
//                    checkedListener.getCheckBoxChecked(position);
//                }
            }
        });
//        checkbox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(checkbox.isChecked()) {
//                    sum += Double.parseDouble(farmerArrayListData.get(position).getCultivationArea());
//                    Toast.makeText(view.getContext(), "Total Cultivation Area = " + sum, Toast.LENGTH_LONG).show();
//                    String str_sum = String.valueOf(sum);
//                    //userSessionManager.setAreaCultivation(str_sum);
//                    Log.d("sum", "sum : " + str_sum);
//                }else {
//                    sum -= Double.parseDouble(farmerArrayListData.get(position).getCultivationArea());
//                    Toast.makeText(view.getContext(), "Total Cultivation Area = " + sum, Toast.LENGTH_LONG).show();
//                    String str_sum = String.valueOf(sum);
//                    //userSessionManager.setAreaCultivation(str_sum);
//                    Log.d("sum", "sum : " + str_sum);
//                }
//
//            }
//        });

        //totalCultivationAreaTextView.setText(""+sum);

        //farmerImage.setImageResource(farmerArrayListData.get(position).getFarmerPicture());

        Glide.with(context).load(farmerArrayListData.get(position).getFarmerPicture()).into(farmerImage);
        //Picasso.get().load(farmerArrayListData.get(position).getFarmerPicture()).into(farmerImage);

        farmerId.setText(farmerArrayListData.get(position).getFarmerID());
        farmerName.setText(farmerArrayListData.get(position).getFarmerName());
        lastCutting.setText(farmerArrayListData.get(position).getLastCutting());
        cultivation_area_farmer.setText(farmerArrayListData.get(position).getCultivationArea()+ " dcml");

        return view;
    }

    public interface CheckBoxCheckedListener{
        void getCheckBoxChecked(int position);
        void getCheckBoxRemoved(int position);
    }

    public void setCheckedListener(CheckBoxCheckedListener checkedListener){
        this.checkedListener = checkedListener;
        this.RemovedListener = checkedListener;
    }


}
