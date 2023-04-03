package com.example.pant.controller;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pant.R;
import com.example.pant.modele.Appoint;
import com.example.pant.modele.AppointAdaptaterFutur;

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

public class loginPage extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout appointfutur, appointpast, takeappoint, report, logout;
    ListAdapter listAdapter;
    ArrayList<Appoint> dataArrayList = new ArrayList<>();
    Appoint appoint;
    ListView listView;

    public String id_user = "c.omputer";

    private JSONArray[] appointList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        listView = findViewById(R.id.listview);


        AppointFutur lg = new AppointFutur(loginPage.this);
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

        for (int i = 0; i < data.length(); i++) {
            try {
                JSONObject obj = new JSONObject(data.getString(i));
                Appoint appoint = new Appoint(obj.getString("date_appoint"), obj.getString("hour_appoint"), obj.getString("label_client"), obj.getString("nom_client"), obj.getString("prenom_client"), obj.getInt("id_client"), obj.getInt("id_appoint"));
                dataArrayList.add(appoint);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        listAdapter = new AppointAdaptaterFutur(loginPage.this, dataArrayList);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(loginPage.this, report.class);
                startActivity(intent);
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

    class AppointFutur extends AsyncTask<String, Void, JSONObject> {

        Context context;
        ProgressDialog progressDialog;


        AppointFutur(Context ctx) {
            this.context = ctx;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(context, "", "Récupération des données");
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            String appointfutur_url = "https://pant-gsb.ovh/api/appoint_futur_api.php";
            try {
                URL url = new URL(appointfutur_url);
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
                Toast.makeText(context, "cbon", Toast.LENGTH_LONG).show();
            } else if (status == HttpURLConnection.HTTP_BAD_REQUEST) {
                Toast.makeText(context, "Incorrect username or password", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Failed to connect to server. Please check your internet connection and try again.", Toast.LENGTH_LONG).show();
            }
        }
        }

    }
