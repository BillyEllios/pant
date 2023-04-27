package com.example.pant.modele;

public class Team {

    String mail;
    String name;
    String surname;

    public Team(String mail, String name, String surname){
        this.mail=mail;
        this.name=name;
        this.surname=surname;
    }
    public String getMail() {
        return mail;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

}
