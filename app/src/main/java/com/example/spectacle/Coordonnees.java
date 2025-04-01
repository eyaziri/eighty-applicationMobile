package com.example.spectacle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Coordonnees extends AppCompatActivity {

    private Event event; // L'événement en cours
    private EditText editTextNom, editTextPrenom, editTextEmail,editTextPlaces;
    private Button buttonReserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordonnees);

        // Récupérer l'événement depuis l'Intent
        event = (Event) getIntent().getSerializableExtra("event");

        if (event == null) {
            Toast.makeText(this, "Erreur : Aucun événement reçu.", Toast.LENGTH_SHORT).show();
            finish(); // Ferme l'activité si aucun événement n'est reçu
            return;
        }

        // Lier les vues
        editTextNom = findViewById(R.id.editTextText);
        editTextPrenom = findViewById(R.id.editTextText2);
        editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        editTextPlaces = findViewById(R.id.editTextText3);
        buttonReserver = findViewById(R.id.button8);

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
            Toast.makeText(this, "Veuillez entrer un nombre de places", Toast.LENGTH_SHORT).show();
            return;
        }

        int nombrePlaces = Integer.parseInt(nombrePlacesStr);

        if (nombrePlaces <= 0) {
            Toast.makeText(this, "Le nombre de places doit être supérieur à 0", Toast.LENGTH_SHORT).show();
            return;
        }

        if (event != null) {
            if (event.getNombreDePlace() >= nombrePlaces) {
                event.decrementerPlaces(nombrePlaces);

                Toast.makeText(this, "Réservation réussie ! Places restantes : " + event.getNombreDePlace(), Toast.LENGTH_SHORT).show();

                // Vérifier si des places restent après la réservation
                if (event.getNombreDePlace() > 0) {
                    // Redirection vers l'interface de succès
                    Intent intent = new Intent(Coordonnees.this, ReservationSucces.class);
                    startActivity(intent);
                } else {
                    // Redirection vers l'interface d'échec (plus de places disponibles)
                    Intent intent = new Intent(Coordonnees.this, EchecReservation.class);
                    startActivity(intent);
                }

                finish(); // Fermer l'activité actuelle après la transition
            } else {
                Toast.makeText(this, "Pas assez de places disponibles", Toast.LENGTH_SHORT).show();
            }
        }
    }

        private void insererDansBaseDeDonnees(String nom, String prenom, String email, int nombrePlaces, Event event) {
            String url = "jdbc:mysql://localhost:3306/reservations_db"; // Remplace par l'IP de ton serveur MySQL
            String user = "root"; // Remplace par ton utilisateur MySQL
            String password = "f56724"; // Remplace par ton mot de passe MySQL

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                String sql = "INSERT INTO personne (nom, prenom, email, nombreDePlace, titreDeFilm, dateFilm, dureeFilm) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, nom);
                stmt.setString(2, prenom);
                stmt.setString(3, email);
                stmt.setInt(4, nombrePlaces);
                stmt.setString(5, event.getTitle());  // Titre du film
                stmt.setString(6, event.getDate());   // Date du film
                stmt.setString(7, event.getDuration());  // Durée du film

                stmt.executeUpdate();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                Toast.makeText(this, "Erreur lors de la connexion à la base de données", Toast.LENGTH_SHORT).show();
            }
    }

}
