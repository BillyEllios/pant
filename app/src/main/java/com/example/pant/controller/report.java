package com.example.pant.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.pant.R;
import com.example.pant.modele.Report;
import com.example.pant.modele.ReportAdaptater;

import java.util.ArrayList;

public class report extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout appointfutur, appointpast, takeappoint, report, logout;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        appointfutur = findViewById(R.id.appointfutur);
        appointpast = findViewById(R.id.appointpast);
        takeappoint = findViewById(R.id.takeappoint);
        report = findViewById(R.id.report);
        logout = findViewById(R.id.logout);


        listView = findViewById(R.id.listView);

        // Create Data
        ArrayList<Report> arrayList = new ArrayList<>();
        arrayList.add(new Report("Je suis une tortue", "Je suis intéressé", "24/01/2023","button", "comment"));
        arrayList.add(new Report("Je suis une tortue", "Je suis intéressé", "24/01/2023","button", "comment"));
        arrayList.add(new Report("Je suis une tortue", "Je suis intéressé", "24/01/2023","button", "comment"));
        arrayList.add(new Report("Je suis une tortue", "Je suis intéressé", "24/01/2023","button", "comment"));

        // Custom Adaptater
        ReportAdaptater reportAdaptater = new ReportAdaptater(this,R.layout.report_list_view,arrayList);
        listView.setAdapter(reportAdaptater);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        appointfutur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(report.this, loginPage.class);
            }
        });
        appointpast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(report.this, appointPast.class);
            }
        });
        takeappoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(report.this, report.class);
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