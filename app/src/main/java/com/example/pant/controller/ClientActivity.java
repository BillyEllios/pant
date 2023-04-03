package com.example.pant.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.pant.R;
import com.example.pant.modele.Client;
import com.example.pant.modele.ClientAdaptater;

import java.util.ArrayList;

public class ClientActivity extends AppCompatActivity {

    ListView listViewClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_client);

        listViewClient = findViewById(R.id.listViewClient);

        // Create Data
        ArrayList<Client> arrayList = new ArrayList<>();

        arrayList.add(new Client("Jean","Tartine","69110","tartine@gmail.com","Lyon","25 Avenue du cul","0769582110","Clinique","Blabla"));
        arrayList.add(new Client("Jean","Tartine","69110","tartine@gmail.com","Lyon","25 Avenue du cul","0769582110","Clinique","Blabla"));
        arrayList.add(new Client("Jean","Tartine","69110","tartine@gmail.com","Lyon","25 Avenue du cul","0769582110","Clinique","Blabla"));
        arrayList.add(new Client("Jean","Tartine","69110","tartine@gmail.com","Lyon","25 Avenue du cul","0769582110","Clinique","Blabla"));
        arrayList.add(new Client("Jean","Tartine","69110","tartine@gmail.com","Lyon","25 Avenue du cul","0769582110","Clinique","Blabla"));

        // Custom Adaptater
        ClientAdaptater clientAdaptater = new ClientAdaptater(this,R.layout.info_list_client,arrayList);
        listViewClient.setAdapter(clientAdaptater);
    }
}