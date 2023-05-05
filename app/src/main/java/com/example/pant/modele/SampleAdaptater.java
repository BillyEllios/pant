package com.example.pant.modele;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pant.R;

import java.util.ArrayList;

public class SampleAdaptater extends ArrayAdapter<Sample> {
    public SampleAdaptater(@NonNull Context context, ArrayList<Sample> dataArrayList) {
        super(context, R.layout.sample_list, dataArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        Sample listData = getItem(position);

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.sample_list, parent, false);
        }

        TextView sample=view.findViewById(R.id.SampleName);

        sample.setText(listData.getLabel_sample());

        return view;

    }
}
