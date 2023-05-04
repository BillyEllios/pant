package com.example.pant.controller;

import static com.example.pant.modele.user.getMetier;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pant.R;
import com.example.pant.modele.Report;
import com.example.pant.modele.ReportAdaptater;
import com.example.pant.modele.api;
import com.example.pant.modele.user;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ReportModify extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout appointfutur, appointpast, takeappoint, report, logout, team;
    Button button;
    Spinner interest;
    EditText report_modif;
    String choice,update_report;
    int id_report = Report.id_report;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_modify);

        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        appointfutur = findViewById(R.id.appointfutur);
        appointpast = findViewById(R.id.appointpast);
        takeappoint = findViewById(R.id.takeappoint);
        report = findViewById(R.id.report);
        team=findViewById(R.id.myteam);
        logout = findViewById(R.id.logout);

        report_modif = findViewById(R.id.report_modif);
        interest = (Spinner) findViewById(R.id.interest);
        button = findViewById(R.id.button);
        loadSpinnerData();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice = interest.getSelectedItem().toString();
                update_report = report_modif.getText().toString();

                try {
                    sendapi();
                    Intent updateReport = new Intent(ReportModify.this, report.class);
                    startActivity(updateReport);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        });


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        appointfutur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ReportModify.this, loginPage.class);
                finish();
            }
        });
        appointpast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ReportModify.this, appointPast.class);
                finish();
            }
        });
        takeappoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ReportModify.this, takeAppoint.class);
                finish();
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ReportModify.this, report.class);
                finish();
            }
        });
        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int d=getMetier();
                if(getMetier()==2){
                    redirectActivity(ReportModify.this, listTeam.class);
                    finish();
                }
                else{
                    Toast.makeText(ReportModify.this, "vous n'avez pas l'autorisation", Toast.LENGTH_LONG).show();
                }
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

    private void loadSpinnerData() {
        String req;
        ArrayList<String> arrayInterest = new ArrayList<String>();
        arrayInterest.add("Pas intéressé");
        arrayInterest.add("Intéressé à revoir");
        arrayInterest.add("Très intéressé");
        ArrayAdapter<String> dataAdapteInterest;
        dataAdapteInterest = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayInterest);
        interest.setAdapter(dataAdapteInterest);
    }

    private void sendapi() throws ExecutionException, InterruptedException {
        String req = null;
        /*
        report_update = report_modif.toString();
        report_modif = findViewById(R.id.report_modif);
        */
        try {
            req = URLEncoder.encode("id_report", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(id_report)) + "&" +
                    URLEncoder.encode("interest", "UTF-8") + "=" + URLEncoder.encode(choice, "UTF-8")+ "&" +
                    URLEncoder.encode("summary", "UTF-8") + "=" + URLEncoder.encode(update_report, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(req);
        api updateReport = new api(ReportModify.this, req, "https://pant-gsb.ovh/api/update_report_api.php");
        updateReport.execute();


    }
}
