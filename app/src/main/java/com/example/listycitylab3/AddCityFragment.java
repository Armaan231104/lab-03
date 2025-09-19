package com.example.listycitylab3;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    public interface AddCityDialogListener {
        void addCity(City city);
        void editCity(int index, String name, String province);
    }

    public static AddCityFragment newInstance(City city, int index) {
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        args.putInt("index", index);
        AddCityFragment f = new AddCityFragment();
        f.setArguments(args);
        return f;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext())
                .inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        City toEdit = null;
        int index = -1;
        Bundle args = getArguments();
        if (args != null) {
            toEdit = (City) args.getSerializable("city");
            index = args.getInt("index", -1);
        }
        final int editIndex = index;
        if (toEdit != null) {
            editCityName.setText(toEdit.getName());
            editProvinceName.setText(toEdit.getProvince());
        }

        boolean isEditing = (toEdit != null && editIndex >= 0);
        AddCityFragment.AddCityDialogListener listener =
                (AddCityFragment.AddCityDialogListener) requireActivity();

        return new AlertDialog.Builder(requireContext())
                .setTitle(isEditing ? "Edit city" : "Add a city")
                .setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton(isEditing ? "Save" : "Add", (d, w) -> {
                    String name = editCityName.getText().toString();
                    String province = editProvinceName.getText().toString();
                    if (isEditing) {
                        listener.editCity(editIndex, name, province);
                    } else {
                        listener.addCity(new City(name, province));
                    }
                })
                .create();
    }
}
