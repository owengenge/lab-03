package com.example.listycitylab3;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {
    interface AddCityDialogListener {
        void addCity(City city);
        void updateCity(int position, City updatedCity);
    }

    private AddCityDialogListener listener;

    // ARG keys
    private static final String ARG_CITY = "arg_city";
    private static final String ARG_POS  = "arg_pos";

    /** Recommended pattern */
    public static AddCityFragment newInstance(@Nullable City city, int position) {
        Bundle args = new Bundle();
        if (city != null) args.putSerializable(ARG_CITY, city);
        args.putInt(ARG_POS, position);           // use -1 when adding
        AddCityFragment f = new AddCityFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // Read args to decide Add vs Edit
        Bundle args = getArguments();
        City existing = (args != null) ? (City) args.getSerializable(ARG_CITY) : null;
        int position = (args != null) ? args.getInt(ARG_POS, -1) : -1;

        if (existing != null) {
            editCityName.setText(existing.getName());
            editProvinceName.setText(existing.getProvince());
        }

        String title = (existing == null) ? "Add a city" : "Edit city";

        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle(title)
                .setNegativeButton("Cancel", null)
                .setPositiveButton((existing == null) ? "Add" : "Save", (dialog, which) -> {
                    String cityName = editCityName.getText().toString().trim();
                    String provinceName = editProvinceName.getText().toString().trim();
                    if (cityName.isEmpty() || provinceName.isEmpty()) return;

                    City updated = new City(cityName, provinceName);
                    if (position >= 0) {
                        listener.updateCity(position, updated);
                    } else {
                        listener.addCity(updated);
                    }
                })
                .create();
    }
}
