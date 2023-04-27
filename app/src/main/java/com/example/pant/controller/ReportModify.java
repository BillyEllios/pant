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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pant.R;
import com.example.pant.modele.Report;
import com.example.pant.modele.ReportAdaptater;
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

public class ReportModify extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout appointfutur, appointpast, takeappoint, report, logout, team;
    ListView listView;
    public String id_user = user.id_user;
    Spinner interest;
    EditText report_modif;
    int id_report = 36;

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

        loadSpinnerData();

        listView = findViewById(R.id.listView);


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
            }
        });
        appointpast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ReportModify.this, appointPast.class);
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
                redirectActivity(ReportModify.this, report.class);
            }
        });
        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int d=getMetier();
                if(getMetier()==2){
                    redirectActivity(ReportModify.this, listTeam.class);
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


        class ModifyReport extends AsyncTask<String, Void, JSONObject> {

            Context context;
            ProgressDialog progressDialog;


            ModifyReport(Context ctx) {
                this.context = ctx;
            }

            @Override
            protected void onPreExecute() {
                progressDialog = ProgressDialog.show(context, "", "Récupération des données");
            }

            @Override
            protected JSONObject doInBackground(String... params) {
                String report_url = "https://pant-gsb.ovh/api/update_report_api.php";
                try {
                    URL url = new URL(report_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setConnectTimeout(10000);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream os = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("id_report", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(id_report), "UTF-8");
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
                int status = 0;
                try {
                    status = jsonObject.getInt("status");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                if (status == HttpURLConnection.HTTP_OK) {
                    Toast.makeText(context, "cbon", Toast.LENGTH_LONG).show();
                } else if (status == HttpURLConnection.HTTP_BAD_REQUEST) {
                    Toast.makeText(context, "Incorrect username or password", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Failed to connect to server. Please check your internet connection and try again.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
