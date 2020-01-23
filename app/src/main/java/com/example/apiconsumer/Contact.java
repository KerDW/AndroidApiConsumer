package com.example.apiconsumer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contact {

    @SerializedName("nom")
    @Expose
    public String nom;

    @SerializedName("cognoms")
    @Expose
    public String cognoms;

    public Contact(String nom, String cognoms) {
        this.nom = nom;
        this.cognoms = cognoms;
    }

    public Contact() {

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognoms() {
        return cognoms;
    }

    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }
}
