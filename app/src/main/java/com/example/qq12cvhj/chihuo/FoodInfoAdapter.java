package com.example.qq12cvhj.chihuo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by qq12cvhj on 2018/3/23.
 */

public class FoodInfoAdapter extends ArrayAdapter<FoodInfo> {
    private int resourceId;
    public FoodInfoAdapter(@NonNull Context context,  int textViewResourceId, @NonNull List<FoodInfo> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        FoodInfo  foodInfo = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        /** 由于是通用列表适配器，根据状态码进行列表项渲染 */
        switch (commonInfo.viewChangeStatus){
            /** 1:用于搜索界面 */
            case 1:
                TextView idText = (TextView) view.findViewById(R.id.search_food_id);
                TextView nameText = (TextView) view.findViewById(R.id.search_food_name);
                idText.setText(String.valueOf(foodInfo.foodId));
                nameText.setText(foodInfo.foodName);
                break;
        }
        return view;
    }
}