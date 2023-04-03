package com.example.pant.modele;

public class Client {

    String FName;
    String LName;
    String Postal;
    String Mail;
    String City;
    String Address;
    String Phone;
    String Label;
    String Comment;

    public Client(String fname, String lname, String postal, String mail, String city, String address, String phone, String label, String comment) {
        FName = fname;
        LName = lname;
        Postal = postal;
        Mail = mail;
        City = city;
        Address = address;
        Phone = phone;
        Label = label;
        Comment = comment;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getPostal() {
        return Postal;
    }

    public void setPostal(String postal) {
        Postal = postal;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
