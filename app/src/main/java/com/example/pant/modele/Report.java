package com.example.pant.modele;

public class Report {

    String Rapport;
    String Interet;
    String Date;
    String Client;
    String Comment;
    int id_client;
    public Report(String Client, String rapport, String interet,String Date, String Comment) {
        this.Rapport = rapport;
        this.Interet = interet;
        this.Date = Date;
        this.Comment = Comment;
        this.id_client = id_client;
        this.Client = Client;
    }

    public String getRapport() {
        return Rapport;
    }

    public String getInteret() {
        return Interet;
    }

    public String getDate() {
        return Date;
    }

    public String getComment() {
        return Comment;
    }

    public String getClient() {
        return Client;
    }

    public int getId_client() {
        return id_client;
    }
}
