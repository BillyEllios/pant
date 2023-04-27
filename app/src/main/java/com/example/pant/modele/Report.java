package com.example.pant.modele;

public class Report {
    public static int id_report;
    String Rapport;
    String Interet;
    String Date;

    int id_client;
    public Report(String rapport, String interet,String Date) {
        this.Rapport = rapport;
        this.Interet = interet;
        this.Date = Date;
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

    public int getId_report() {
        return id_report;
    }
}
