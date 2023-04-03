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

public class ClientAdaptater extends ArrayAdapter<Client> {
    private Context mContext;
    private int mRessource;

    public ClientAdaptater(@NonNull Context context, int resource, @NonNull ArrayList<Client> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mRessource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mRessource,parent,false);

        TextView txtFName = convertView.findViewById(R.id.fname);
        TextView txtLName = convertView.findViewById(R.id.lname);
        TextView txtMail = convertView.findViewById(R.id.mail);
        TextView txtPostal = convertView.findViewById(R.id.cp);
        TextView txtCity = convertView.findViewById(R.id.city);
        TextView txtAddress = convertView.findViewById(R.id.adress);
        TextView txtPhone = convertView.findViewById(R.id.phone);
        TextView txtLabel = convertView.findViewById(R.id.label);
        TextView txtComment = convertView.findViewById(R.id.comment);


        txtFName.setText(getItem(position).getFName());
        txtLName.setText(getItem(position).getLName());
        txtMail.setText(getItem(position).getMail());
        txtPostal.setText(getItem(position).getPostal());
        txtCity.setText(getItem(position).getCity());
        txtAddress.setText(getItem(position).getAddress());
        txtPhone.setText(getItem(position).getPhone());
        txtLabel.setText(getItem(position).getLabel());
        txtComment.setText(getItem(position).getComment());

        return convertView;
    }
}
