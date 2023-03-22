package com.example.pant.modele;

import android.widget.Button;

public class Report {

    String Rapport;
    String Interet;
    String Date;
    String Client;
    String Comment;
    public Report(String rapport, String interet, String date, String client, String comment) {
        Rapport = rapport;
        Interet = interet;
        Date = date;
        Client = client;
        Comment = comment;
    }

    public String getRapport() {
        return Rapport;
    }

    public void setRapport(String rapport) {
        Rapport = rapport;
    }

    public String getInteret() {
        return Interet;
    }

    public void setInteret(String interet) {
        Interet = interet;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getClient() {
        return Client;
    }

    public void setClient(String client) {
        Client = client;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
