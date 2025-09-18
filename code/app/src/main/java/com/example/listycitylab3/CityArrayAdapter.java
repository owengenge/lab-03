package com.example.listycitylab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CityArrayAdapter extends ArrayAdapter {
    public CityArrayAdapter(Context context, ArrayList<City> cities) {
        super(context, 0, cities);
    }

    @NonNull
    @Override
    // Position: place in list
    // convertView: Reusable view
    // ViewGroup: holds all the views together
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup
            parent) {
        View view;

        if (convertView == null) {
            // New view
            view = LayoutInflater.from(getContext()).inflate(R.layout.content,
                    parent, false);
        } else {
            // Old view were reusing
            view = convertView;
        }
        City city = (City) getItem(position);
        TextView cityName = view.findViewById(R.id.city_text);
        TextView provinceName = view.findViewById(R.id.province_text);
        cityName.setText(city.getName());
        provinceName.setText(city.getProvince());
        return view;
    }
}
