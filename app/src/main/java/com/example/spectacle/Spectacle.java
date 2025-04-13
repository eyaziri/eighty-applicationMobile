package com.example.spectacle;

import com.google.gson.annotations.SerializedName;

public class Spectacle {

    @SerializedName("idSpec")
    private int idSpec;

    @SerializedName("Titre")
    private String titre;

    @SerializedName("dateS")
    private String dateS;

    @SerializedName("h_debut")
    private String h_debut;

    @SerializedName("dureeS")
    private String dureeS;

    @SerializedName("nbrSpectateur")
    private int nbrSpectateur;

    @SerializedName("idLieu")
    private String idLieu;

    @SerializedName("genre")
    private String genre;

    @SerializedName("urlImage")
    private String urlImage;

    @SerializedName("urlTrailer")
    private String urlTrailer;

    // Getters
    public int getIdSpec() { return idSpec; }

    public String getTitre() { return titre; }

    public String getDateS() { return dateS; }

    public String getH_debut() { return h_debut; }

    public String getDureeS() { return dureeS; }

    public int getNbrSpectateur() { return nbrSpectateur; }

    public String getIdLieu() { return idLieu; }

    public String getGenre() { return genre; }

    public String getUrlImage() { return urlImage; }

    public String getUrlTrailer() { return urlTrailer; }
}
