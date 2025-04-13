package com.example.spectacle;

public class Reservation {
    private String nom;
    private String prenom;
    private String email;
    private int nombreDePlace;
    private int idSpec;


    // Constructeur
    public Reservation(
            String nom,
            String prenom,
            String email,
            int nombreDePlace,
            int idSpec) {
        this.nom = nom;
        this.email = email;
        this.prenom=prenom ;
        this.nombreDePlace=nombreDePlace;
        this.idSpec=idSpec;
    }

    // Getters (nécessaires pour la sérialisation JSON)
    public String getNom() { return nom; }
    public String getEmail() { return email; }
    public String getPrenom() { return prenom; }
    public int getnombreDePlace() { return nombreDePlace; }
    public int getidSpec() { return idSpec; }


    public void setNom(String nom) { this.nom = nom; }
    public void setEmail(String email) { this.email = email; }

    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setNombreDePlace(int nombreDePlace) { this.nombreDePlace = nombreDePlace; }
    public void setidSpec(int idSpec) { this.idSpec = idSpec; }

}