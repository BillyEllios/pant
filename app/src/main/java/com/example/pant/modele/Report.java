package com.example.pant.modele;

public class Report {

    String Rapport;
    String Interet;
    String Date;

    int id_client;
    public Report(String rapport, String interet,String Date) {
        this.Rapport = rapport;
        this.Interet = interet;
        this.Date = Date;
        this.id_client = id_client;
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

    public int getId_client() {
        return id_client;
    }
}
