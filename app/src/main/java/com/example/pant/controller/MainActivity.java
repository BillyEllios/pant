package com.example.pant.controller;

import static android.widget.Toast.*;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.io.UnsupportedEncodingException;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import com.example.pant.R;
import com.example.pant.modele.api;
import com.example.pant.modele.user;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button login;
    String userVar, passVar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.Login);
        password = findViewById(R.id.MDP);
        login = findViewById(R.id.button);




        login.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                try

                {
                userVar = username.getText().toString();
                passVar = password.getText().toString();

                if (userVar.equals("")) {
                    makeText(getApplicationContext(), "Username cannot be blank", LENGTH_SHORT).show();
                } else if (passVar.equals("")) {
                    makeText(getApplicationContext(), "password cannot be blank", LENGTH_SHORT).show();
                } else {

                    String req = null;
                    try {
                        req = URLEncoder.encode("id_user", "UTF-8") + "=" + URLEncoder.encode(userVar, "UTF-8") + "&" +
                                URLEncoder.encode("pwd_user", "UTF-8") + "=" + URLEncoder.encode(passVar, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }

                    api lg = new api(MainActivity.this, req, "https://pant-gsb.ovh/api/connection/connection.php");

                    lg.execute();

                    try {
                        JSONObject response= lg.get();

                        user.id_user = userVar;

                        if (response.getInt("status") == 200) {
                            lg.setToken(response.getJSONObject("data").getString("token"));
                            user.setMetier(Integer.parseInt(response.getJSONObject("data").getString("id_job")));
                            Intent loginPageIntent = new Intent(MainActivity.this, loginPage.class);
                            startActivity(loginPageIntent);
                        }if (response.getInt("status") == 400){
                            Intent loginPageIntent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(loginPageIntent);
                            finish();
                        }
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                    //lg.setToken();
                }
            } catch (Exception e){
                    Toast.makeText(MainActivity.this , "attention vous n'avez pas de connection internet", Toast.LENGTH_LONG).show();
                    Intent loginPageIntent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(loginPageIntent);
                }
            }

        });


    }
    public static boolean isInternetAvailable() {
        try {
            InetAddress.getByName("google.com");
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }
}
