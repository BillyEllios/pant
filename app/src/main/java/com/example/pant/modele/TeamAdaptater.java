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

public class TeamAdaptater extends ArrayAdapter<Team> {
    public TeamAdaptater(@NonNull Context context, ArrayList<Team> dataArrayList) {
        super(context, R.layout.team_list, dataArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        Team listData = getItem(position);

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.team_list, parent, false);
        }

        TextView mail = view.findViewById(R.id.ListMail);
        TextView name = view.findViewById(R.id.Listname);
        TextView surname = view.findViewById(R.id.Listsurname);

        mail.setText(listData.getMail());
        name.setText(listData.getName());
        surname.setText(listData.getSurname());



        return view;

    }
}
