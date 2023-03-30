package com.example.pant.modele;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pant.R;

import java.util.ArrayList;

public class AppointAdaptaterPast extends ArrayAdapter<Appoint> {
    public AppointAdaptaterPast(@NonNull Context context, ArrayList<Appoint> dataArrayList, int xml) {
        super(context, xml, dataArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        Appoint listData = getItem(position);

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.appoint_past_list, parent, false);
        }

        TextView date = view.findViewById(R.id.listDate);
        TextView time = view.findViewById(R.id.ListTime);
        TextView type = view.findViewById(R.id.Type);
        TextView name = view.findViewById(R.id.name);
        TextView surname = view.findViewById(R.id.surname);

        date.setText(listData.date);
        time.setText(listData.time);
        type.setText(listData.label);
        name.setText(listData.name);
        surname.setText(listData.surname);

        return view;

    }
}
