package com.example.spectacle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Coordonnees extends AppCompatActivity {

    private EditText editTextNom, editTextPrenom, editTextEmail, editTextPlaces, prixValeur;
    private Button buttonReserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordonnees);

        editTextNom = findViewById(R.id.editTextNom);
        prixValeur = findViewById(R.id.PrixValeur);
        editTextPrenom = findViewById(R.id.editTextPrenom);
        editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        editTextPlaces = findViewById(R.id.editTextPlaces);
        buttonReserver = findViewById(R.id.button8);

        // Listener pour calculer le prix total en fonction du nombre de places
        editTextPlaces.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                try {
                    int nombreDePlaces = Integer.parseInt(charSequence.toString());
                    int prixUnitaire = 10; // Prix unitaire par place en TND (modifiable selon votre logique)
                    int prixTotal = nombreDePlaces * prixUnitaire;
                    prixValeur.setText(String.valueOf(prixTotal));
                } catch (NumberFormatException e) {
                    prixValeur.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        buttonReserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reserverPlaces();
            }
        });
    }

    private void reserverPlaces() {
        String nom = editTextNom.getText().toString().trim();
        String prenom = editTextPrenom.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String nombrePlacesStr = editTextPlaces.getText().toString().trim();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || nombrePlacesStr.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
            return;
        }

        int nombrePlaces = Integer.parseInt(nombrePlacesStr);
        int idSpec = getIntent().getIntExtra("idSpec", 1); // 1 est la valeur par défaut

        // Créer l'objet Reservation
        Reservation reservation = new Reservation(nom, prenom, email, nombrePlaces, idSpec);

        // Appeler Retrofit pour envoyer la réservation
        ApiService apiService = RetrofitClient.getApiService();
        Call<Void> call = apiService.enregistrerReservation(reservation);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Coordonnees.this, "Réservation réussie !", Toast.LENGTH_SHORT).show();
                    // Naviguer vers l'activité de paiement
                    Intent intent = new Intent(Coordonnees.this, payement.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Coordonnees.this, "Erreur lors de la réservation. Code : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Coordonnees.this, "Erreur de connexion : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API", "Erreur de connexion", t);
            }
        });
    }

}
