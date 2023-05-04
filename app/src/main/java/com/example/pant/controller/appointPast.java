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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pant.R;
import com.example.pant.modele.Appoint;
import com.example.pant.modele.AppointAdaptaterFutur;
import com.example.pant.modele.AppointAdaptaterPast;
import com.example.pant.modele.Client;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class appointPast extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout appointfutur, appointpast, takeappoint, report, logout, team;

    ListAdapter listAdapter;
    ArrayList<Appoint> dataArrayList = new ArrayList<>();
    Appoint appoint;
    ListView listView;

    private String id_user= user.id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_past);

        listView = findViewById(R.id.listview);


        AppointPast lg = new AppointPast(appointPast.this);
        lg.execute();

        JSONObject reponse;

        JSONArray data;
        try {
            reponse = lg.get();
            data = reponse.getJSONArray("data");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(data.length()==0){
            String TextAfficher="Vous n'avez aucun compte-rendu à saisir!";
            TextView textVide = findViewById(R.id.textvide);
            textVide.setText(TextAfficher);
        }

        int[] listIdClient = new int[data.length()];
        int[] listIdAppoint = new int[data.length()];

        for (int i = 0; i < data.length(); i++) {
            try {
                JSONObject obj = new JSONObject(data.getString(i));
                Appoint appoint = new Appoint(obj.getString("date_appoint"), obj.getString("hour_appoint"), obj.getString("label_client"), obj.getString("nom_client"), obj.getString("prenom_client"), obj.getInt("id_client"), obj.getInt("id_appoint"));
                dataArrayList.add(appoint);
                listIdClient[i]=obj.getInt("id_client");
                listIdAppoint[i]=obj.getInt("id_appoint");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        listAdapter = new AppointAdaptaterPast(appointPast.this, dataArrayList);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(appointPast.this, makeReport.class);
                Appoint.id_client=listIdClient[i];
                Appoint.id_appoint=listIdAppoint[i];
                startActivity(intent);
                finish();
            }
        });

        //navigation drawer

        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        appointfutur = findViewById(R.id.appointfutur);
        appointpast = findViewById(R.id.appointpast);
        takeappoint = findViewById(R.id.takeappoint);
        report = findViewById(R.id.report);
        logout = findViewById(R.id.logout);
        team=findViewById(R.id.myteam);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        appointfutur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(appointPast.this, loginPage.class);
                finish();
            }
        });
        appointpast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        takeappoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(appointPast.this, takeAppoint.class);
                finish();
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(appointPast.this, report.class);
                finish();
            }
        });
        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int d=getMetier();
                if(getMetier()==2){
                    redirectActivity(appointPast.this, listTeam.class);
                    finish();
                }
                else{
                    Toast.makeText(appointPast.this, "vous n'avez pas l'autorisation", Toast.LENGTH_LONG).show();
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

    class AppointPast extends AsyncTask<String, Void, JSONObject> {

        Context context;
        ProgressDialog progressDialog;


        AppointPast(Context ctx) {
            this.context = ctx;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(context, "", "Récupération des données");
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            String appointpast_url = "https://pant-gsb.ovh/api/appoint_past_api.php";
            try {
                URL url = new URL(appointpast_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("id_user", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(id_user), "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    bufferedReader.close();
                    inputStream.close();

                    JSONObject jsonObject = new JSONObject(response.toString());
                    return jsonObject;
                } else {
                    return new JSONObject(String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST));
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                try {
                    return new JSONObject(String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST));
                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }


        protected void onPostExecute(JSONObject jsonObject) {
            progressDialog.dismiss();
            int status=0;
            try {
                status = jsonObject.getInt("status");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            if (status == HttpURLConnection.HTTP_OK) {
            } else if (status == HttpURLConnection.HTTP_BAD_REQUEST) {
                Toast.makeText(context, "Incorrect username or password", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Failed to connect to server. Please check your internet connection and try again.", Toast.LENGTH_LONG).show();
            }
        }
    }
}