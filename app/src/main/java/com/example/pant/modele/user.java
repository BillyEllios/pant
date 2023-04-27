package com.example.pant.modele;

import android.content.Context;

public class user {

    public static String id_user;
    private String mLogin;
    private String mMotDePasse;
    private static int mMetier;

    public String getLogin() {
        return mLogin;
    }

    public void setLogin(String login) {
        mLogin = login;
    }

    public String getMotDePasse() {
        return mMotDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        mMotDePasse = motDePasse;
    }

    public static int getMetier() {
        return mMetier;
    }

    public static void setMetier(int metier) {
        mMetier = metier;
    }



    public user(String login, String motDePasse, int     metier) {
        this.mLogin = login;
        this.mMotDePasse = motDePasse;
        this.mMetier = metier;
    }
}

