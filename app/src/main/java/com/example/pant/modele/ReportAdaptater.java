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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ReportAdaptater extends ArrayAdapter<Report> {
    public ReportAdaptater(@NonNull Context context, ArrayList<Report> dataArrayList,int xml) {
        super(context, xml, dataArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        Report listData = getItem(position);

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.report_list_view, parent, false);
        }

        TextView rapport = view.findViewById(R.id.rapport);
        TextView interet = view.findViewById(R.id.interet);
        TextView date = view.findViewById(R.id.date);


        rapport.setText(listData.getRapport());
        interet.setText(listData.getInteret());
        date.setText(listData.getDate());

        return view;
    }
}
