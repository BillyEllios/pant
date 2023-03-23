package com.example.pant.modele;

public class Appoint {

    String date;
    String time;
    String label;
    String name;
    String surname;
    int id_client;

    public Appoint(String date, String time, String label, String name, String surname, int id_client) {
        this.date = date;
        this.time = time;
        this.label = label;
        this.name = name;
        this.surname = surname;
        this.id_client = id_client;
    }


    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getId_client() {
        return id_client;
    }
}
