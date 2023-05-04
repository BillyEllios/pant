package com.example.pant.controller;

import static com.example.pant.modele.Client.id_client;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pant.R;
import com.example.pant.modele.Client;

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
import java.util.concurrent.ExecutionException;

public class ClientActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout appointfutur, appointpast, takeappoint, report, logout, team;
    //int id_client= getIntent().getIntExtra("id_client", 0);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_client);

        String id_client=getIntent().getStringExtra("id_client");

        TextView txtFName = findViewById(R.id.fname);
        TextView txtLName = findViewById(R.id.lname);
        TextView txtMail = findViewById(R.id.mail);
        TextView txtPostal = findViewById(R.id.cp);
        TextView txtCity = findViewById(R.id.city);
        TextView txtAddress = findViewById(R.id.adress);
        TextView txtPhone = findViewById(R.id.phone);
        TextView txtLabel = findViewById(R.id.label);

        //int id_client=getIntent().getIntExtra("id_client", 0);


        InfoClient lg = new InfoClient(ClientActivity.this);
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

        Client client = null;
        for (int i = 0; i < data.length(); i++) {
            try {
                JSONObject obj = new JSONObject(data.getString(i));
                client = new Client(/*client.getId_client(),*/ obj.getString("nom_client"), obj.getString("prenom_client"), obj.getString("pc_client"), obj.getString("email_client"), obj.getString("city_client"), obj.getString("address_client"), obj.getString("phone_client"), obj.getString("label_client"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        txtFName.setText(client.getFName());
        txtLName.setText(client.getLName());
        txtMail.setText(client.getMail());
        txtPostal.setText(client.getPostal());
        txtCity.setText(client.getCity());
        txtAddress.setText(client.getAddress());
        txtPhone.setText(client.getPhone());
        txtLabel.setText(client.getLabel());


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

                redirectActivity(ClientActivity.this, loginPage.class);
                finish();
            }
        });
        appointpast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ClientActivity.this, appointPast.class);
                finish();
            }
        });
        takeappoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ClientActivity.this, takeAppoint.class);
                finish();
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ClientActivity.this, report.class);
                finish();
            }
        });
        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int d=getMetier();
                if(getMetier()==2){
                    redirectActivity(ClientActivity.this, listTeam.class);
                    finish();
                }
                else{
                    Toast.makeText(ClientActivity.this, "vous n'avez pas l'autorisation", Toast.LENGTH_LONG).show();
                }
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ClientActivity.this, MainActivity.class);
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

    class InfoClient extends AsyncTask<String, Void, JSONObject> {

        Context context;
        ProgressDialog progressDialog;


        InfoClient(Context ctx) {
            this.context = ctx;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(context, "", "Récupération des données");
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            String appointfutur_url = "https://pant-gsb.ovh/api/info_client_api.php";
            try {
                URL url = new URL(appointfutur_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("id_client", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(id_client), "UTF-8");
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