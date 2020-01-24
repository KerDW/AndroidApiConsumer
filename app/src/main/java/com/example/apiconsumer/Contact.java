package com.example.apiconsumer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contact {

    @SerializedName("contacteId")
    @Expose
    public int contacteId;

    @SerializedName("nom")
    @Expose
    public String nom;

    @SerializedName("cognoms")
    @Expose
    public String cognoms;

    public Contact(int contacteId, String nom, String cognoms) {
        this.contacteId = contacteId;
        this.nom = nom;
        this.cognoms = cognoms;
    }

    public Contact() {

    }

    public int getId() {
        return contacteId;
    }

    public void setId(int contacteId) {
        this.contacteId = contacteId;
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
