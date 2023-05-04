package com.example.pant.controller;

import static com.example.pant.modele.user.getMetier;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;


import com.example.pant.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.CalendarView;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pant.R;
import com.example.pant.modele.api;
import com.example.pant.modele.user;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class takeAppoint extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout appointfutur, appointpast, takeappoint, report, logout, team;
        CalendarView date;
    Spinner hours;
    Spinner client;
    String clientVar,hoursVar,dateVar;
    Button button ;
    private TextView dateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_appoint);

        hours = (Spinner)  findViewById(R.id.hours);
        date = findViewById(R.id.date);
        client = (Spinner) findViewById(R.id.client);
        button = findViewById(R.id.button);
        button.setEnabled(false);
        button.setEnabled(true);

        loadSpinnerData();


        date.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // construire une chaîne de caractères pour afficher la date
                dateVar = year + "-" + (month+1) + "-" + dayOfMonth;
                System.out.println(dateVar);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                date.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        // construire une chaîne de caractères pour afficher la date
                        dateVar = year + "-" + (month+1) + "-" + dayOfMonth;

                    }
                });


                hoursVar = hours.getSelectedItem().toString();
                String clients= client.getSelectedItem().toString();
                String[] result = clients.split("-");
                clientVar = result[0];


                System.out.println(dateVar);
                try {
                    sendapi();
                    Intent takeAppoint = new Intent(takeAppoint.this, loginPage.class);
                    startActivity(takeAppoint);
                    finish();
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
        System.out.println(appointfutur);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        appointfutur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("pol");
                redirectActivity(takeAppoint.this, loginPage.class);
                finish();
            }
        });
        appointpast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("pol");
                redirectActivity(takeAppoint.this, appointPast.class);
                finish();
            }
        });
        takeappoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(takeAppoint.this, takeAppoint.class);
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(takeAppoint.this, report.class);
                finish();
            }
        });
        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int d=getMetier();
                if(getMetier()==2){
                    redirectActivity(takeAppoint.this, listTeam.class);
                    finish();
                }
                else{
                    Toast.makeText(takeAppoint.this, "vous n'avez pas l'autorisation", Toast.LENGTH_LONG).show();
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
        ArrayList<String> arrayHours =  new ArrayList<String>();
        arrayHours.add("8");
        arrayHours.add("9");
        arrayHours.add("10");
        arrayHours.add("11");
        arrayHours.add("12");
        arrayHours.add("14");
        arrayHours.add("15");
        arrayHours.add("16");
        arrayHours.add("17");
        ArrayAdapter<String> dataAdapteHours;
        dataAdapteHours = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayHours);
        hours.setAdapter(dataAdapteHours);

        try {
            req = URLEncoder.encode("token", "UTF-8") + "=" + URLEncoder.encode(api.token, "UTF-8") + "&"+
                    URLEncoder.encode("id_user", "UTF-8") + "=" + URLEncoder.encode(user.id_user, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }


        api lg = new api(takeAppoint.this, req, "https://pant-gsb.ovh/api/client/userClient.php");
        lg.execute();

        JSONObject response;

        try {

            response = lg.get();

            JSONArray data = response.getJSONArray("data");

            int count =response.getInt("count");
            ArrayList<String> clients =  new ArrayList<String>();

            for (int i = 0; i < count; i++) {

                JSONObject jsonobject = data.getJSONObject(i);
                clients.add(jsonobject.getString("id_client")+"-"+jsonobject.getString("nom_client")+":"+jsonobject.getString("prenom_client"));

            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, clients);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            client.setAdapter(dataAdapter);





        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    private void sendapi() throws ExecutionException, InterruptedException {
        String req = null;
        try {
            req = URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(dateVar, "UTF-8") + "&" +
                    URLEncoder.encode("idClient", "UTF-8") + "=" + URLEncoder.encode(clientVar, "UTF-8")+ "&" +
                    URLEncoder.encode("timestamp", "UTF-8") + "=" + URLEncoder.encode(hoursVar, "UTF-8") +"&" +
                    URLEncoder.encode("id_user", "UTF-8") + "=" + URLEncoder.encode(user.id_user, "UTF-8")+"&" +
                    URLEncoder.encode("token", "UTF-8") + "=" + URLEncoder.encode(api.token, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }


        api takeAppoint = new api(takeAppoint.this, req, "https://pant-gsb.ovh/api/appointment/takeAppointment.php");
        takeAppoint.execute();

    }
}