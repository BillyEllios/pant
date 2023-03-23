package com.example.pant.controller;

import static com.example.pant.R.id.toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pant.R;
import com.example.pant.databinding.ActivityMainBinding;
import com.example.pant.modele.Appoint;
import com.example.pant.modele.AppointAdaptater;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class loginPage extends AppCompatActivity  {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout appointfutur, appointpast, takeappoint, report, logout;

    ListAdapter listAdapter;
    ArrayList<Appoint> dataArrayList = new ArrayList<>();
    Appoint appoint;
    ListView listView;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_login_page);
        //setContentView(binding.getRoot());

        listView = findViewById(R.id.listview);

        /*String [] dateList={"23/03/2023", "20/03/2023"};
        String [] timeList={"08:00:00", "18:00:00"};
        String [] nameList={"Bob", "Jean"};
        String [] surnameList={"Papo", "Gégé"};
        String [] labelList={"médecin", "pharmacien"};
        int[] idList={1, 2};*/
        String [][] appointList={
                {"23/03/2023", "08:00:00", "Bob", "Papo", "médecin", "1"},
                {"20/03/2023", "10:00:00", "Jean", "Gégé", "pharmacien", "4"},
                {"17/03/2023", "09:00:00", "Axel", "Dochez", "médecin", "5"}
        };

        for(int i =0; i< appointList.length; i++){
            appoint = new Appoint(appointList[i][0], appointList[i][1], appointList[i][2], appointList[i][3], appointList[i][4], Integer.parseInt(appointList[i][5]));
            dataArrayList.add(appoint);
        }
        listAdapter = new AppointAdaptater(loginPage.this, dataArrayList);
        listView.setAdapter(listAdapter);
        //binding.listview.setClickable(false);

        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        appointfutur=findViewById(R.id.appointfutur);
        appointpast = findViewById(R.id.appointpast);
        takeappoint = findViewById(R.id.takeappoint);
        report = findViewById(R.id.report);
        logout = findViewById(R.id.logout);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        appointfutur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        appointpast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(loginPage.this, appointPast.class);
            }
        });
        takeappoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(loginPage.this, takeAppoint.class);
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(loginPage.this, report.class);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public static void closeDrawer(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent=new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}
