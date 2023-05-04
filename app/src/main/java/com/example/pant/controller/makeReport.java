package com.example.pant.controller;

import static com.example.pant.modele.user.getMetier;
import static com.example.pant.modele.user.id_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pant.R;
import com.example.pant.modele.Appoint;
import com.example.pant.modele.api;
import com.example.pant.modele.user;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class makeReport extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout appointfutur, appointpast, takeappoint, report, logout, team;

    Button mButtonSendReport;

    EditText textReport;

    Spinner interest;
    String choice, make_report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_report);

        mButtonSendReport = findViewById(R.id.buttonSendReport);
        textReport=findViewById(R.id.reportText);
        interest = (Spinner) findViewById(R.id.interest);
        loadSpinnerData();

        mButtonSendReport.setEnabled(false);

        textReport.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mButtonSendReport.setEnabled(true);
            }
        });

        mButtonSendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice = interest.getSelectedItem().toString();
                make_report = textReport.getText().toString();
                System.out.println(choice);
                System.out.println(make_report);
                System.out.println(id_user);
                System.out.println(Appoint.id_appoint);
                System.out.println(Appoint.id_client);

                try {
                    sendReport();
                    Intent makeReportReport = new Intent(makeReport.this, appointPast.class);
                    startActivity(makeReportReport);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //navigation drawer

        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        appointfutur = findViewById(R.id.appointfutur);
        appointpast = findViewById(R.id.appointpast);
        takeappoint = findViewById(R.id.takeappoint);
        report = findViewById(R.id.report);
        team=findViewById(R.id.myteam);
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
                redirectActivity(makeReport.this, loginPage.class);
            }
        });
        appointpast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(makeReport.this, appointPast.class);
            }
        });
        takeappoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(makeReport.this, takeAppoint.class);
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(makeReport.this, report.class);
            }
        });
        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int d=getMetier();
                if(getMetier()==2){
                    redirectActivity(makeReport.this, listTeam.class);
                }
                else{
                    Toast.makeText(makeReport.this, "vous n'avez pas l'autorisation", Toast.LENGTH_LONG).show();
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

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public static void redirectActivity(Activity activity, Class secondActivity) {
        Intent intent = new Intent(activity, secondActivity);
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

    private void sendReport() throws ExecutionException, InterruptedException {
        String req = null;
        String id_user= user.id_user;
        int id_client= Appoint.id_client;
        int id_appoint= Appoint.id_appoint;

        try {
            req =   URLEncoder.encode("id_user", "UTF-8") + "=" + URLEncoder.encode(id_user, "UTF-8")+ "&" +
                    URLEncoder.encode("id_client", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(id_client)) + "&" +
                    URLEncoder.encode("id_appoint", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(id_appoint)) + "&" +
                    URLEncoder.encode("interest", "UTF-8") + "=" + URLEncoder.encode(choice, "UTF-8")+ "&" +
                    URLEncoder.encode("summary", "UTF-8") + "=" + URLEncoder.encode(make_report, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(req);
        api makeReport = new api(makeReport.this, req, "https://pant-gsb.ovh/api/make_report_api.php");
        makeReport.execute();
    }
}