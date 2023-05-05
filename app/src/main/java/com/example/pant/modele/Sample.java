package com.example.pant.modele;

public class Sample {

    public static int id_sample;
    static String label_sample;

    public Sample(int id_sample, String label_sample){
        this.id_sample =id_sample;
        this.label_sample=label_sample;
    }

    public static int getId_sample() {
        return id_sample;
    }

    public static String getLabel_sample() {
        return label_sample;
    }


}
