package com.example.spectacle;

import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface ApiService {
    /*@GET("spectacles")
    Call<List<Spectacle>> getSpectacles();*/
    @POST("personnes")
    @Headers("Content-Type: application/json")
    Call<Void> enregistrerReservation(@Body Reservation reservation);

    // GET - Récupérer tous les spectacles
    @GET("spectacles")
    Call<List<Spectacle>> getSpectacles();

    // POST - Ajouter un spectacle
    @POST("spectacles")
    @Headers("Content-Type: application/json")
    Call<Void> ajouterSpectacle(@Body Spectacle spectacle);

    // GET - Récupérer tous les billets
    @GET("billet")
    Call<List<Billet>> getBillets();

    // POST - Ajouter un billet
    @POST("billet")
    @Headers("Content-Type: application/json")
    Call<Void> ajouterBillet(@Body Billet billet);


    @GET("spectacles/{id}") // Assuming your API endpoint follows this pattern
    Call<Spectacle> getEventById(@Path("id") int idSpec);
}