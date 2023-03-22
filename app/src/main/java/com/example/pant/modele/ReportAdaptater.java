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
    private Context mContext;
    private int mRessource;

    public ReportAdaptater(@NonNull Context context, int resource, @NonNull ArrayList<Report> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mRessource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mRessource,parent,false);

        TextView txtRapport = convertView.findViewById(R.id.rapport);
        TextView txtInteret = convertView.findViewById(R.id.interet);
        TextView txtDate = convertView.findViewById(R.id.date);
        TextView txtClient = convertView.findViewById(R.id.client);
        TextView txtComment = convertView.findViewById(R.id.comment);


        txtRapport.setText(getItem(position).getRapport());
        txtInteret.setText(getItem(position).getInteret());
        txtDate.setText(getItem(position).getDate());
        txtClient.setText(getItem(position).getClient());
        txtComment.setText(getItem(position).getComment());

        return convertView;
    }
}
