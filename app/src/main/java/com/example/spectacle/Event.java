package com.example.spectacle;


import java.io.Serializable;

public class Event implements Serializable {
    private String title;
    private String date;
    private String time;
    private String duration;
    private String location;
    private int imageResId;
    private String genre;

    private int nombreDePlace;

    public Event(String title, String date, String time, String duration, String location, int imageResId , int nombreDePlace ,String genre) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.duration = "Durée :2 Heures";
        this.location = location;
        this.imageResId = imageResId;
        this.nombreDePlace=nombreDePlace;
        this.genre=genre;    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDuration() {
        return duration;
    }

    public String getLocation() {
        return location;
    }

    public int getImageResId() {
        return imageResId;
    }
    public int getNombreDePlace() {
        return nombreDePlace;
    }
    public void decrementerPlaces(int nombre) {
        if (nombreDePlace >= nombre) {
            nombreDePlace -= nombre;
        } else {
            nombreDePlace = 0; // Empêcher les valeurs négatives
        }
    }
}  