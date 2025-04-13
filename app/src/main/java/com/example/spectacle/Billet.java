package com.example.spectacle;

public class Billet {
    private int idBillet;
    private String categorie;
    private double prix;
    private int idSpec;
    private boolean vendu;

    // Constructeur par défaut
    public Billet() {
    }

    // Constructeur avec tous les paramètres
    public Billet(int idBillet, String categorie, double prix, int idSpec, boolean vendu) {
        this.idBillet = idBillet;
        this.categorie = categorie;
        this.prix = prix;
        this.idSpec = idSpec;
        this.vendu = vendu;
    }

    // Getters et Setters

    public int getIdBillet() {
        return idBillet;
    }

    public void setIdBillet(int idBillet) {
        this.idBillet = idBillet;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getIdSpec() {
        return idSpec;
    }

    public void setIdSpec(int idSpec) {
        this.idSpec = idSpec;
    }

    public boolean isVendu() {
        return vendu;
    }

    public void setVendu(boolean vendu) {
        this.vendu = vendu;
    }
}
