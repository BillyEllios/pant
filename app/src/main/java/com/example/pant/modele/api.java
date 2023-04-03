package com.example.pant.modele;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

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

public class api  extends AsyncTask<String, Void, JSONObject> {
    AlertDialog alertDialog;
    Context context;
    String req;
    String url;

    public JSONObject json ;

    ProgressDialog progressDialog;
    public static String token ;

    public api(Context ctx, String apiReq, String url) {
        this.req = apiReq;
        this.url = url;
        this.context = ctx;
    }
    public void setToken (String tokens){
        this.token = tokens ;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(context, "", "Logging you in... Please wait");
    }

    @Override
    public JSONObject doInBackground(String... params) {
        String login_url = this.url;
        try {
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            String data = this.req;
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
                this.json = jsonObject;
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

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        progressDialog.dismiss();
        int status = 0;
        String message = "";
        //System.out.println(jsonObject);
        try {
            status = jsonObject.getInt("status");
            message = jsonObject.getString("message");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        if (status == HttpURLConnection.HTTP_OK) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        } else if (status == HttpURLConnection.HTTP_BAD_REQUEST) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Failed to connect to server. Please check your internet connection and try again.", Toast.LENGTH_LONG).show();
        }
    }
}

