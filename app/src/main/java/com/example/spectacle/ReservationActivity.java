package com.example.spectacle;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ReservationActivity extends AppCompatActivity {

    private EditText editTextText6, editTextText7, editTextText8;
    private TextView textView, textView3, textView4, textView5;
    private ImageView imageView8, imageView9, imageView10;
    private Button btnDecouvrons; // Bouton pour redirection

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        // Initialiser les vues
        editTextText6 = findViewById(R.id.editTextText6);
        editTextText7 = findViewById(R.id.editTextText7);
        editTextText8 = findViewById(R.id.editTextText8);

        textView = findViewById(R.id.textView);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);

        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);
        imageView10 = findViewById(R.id.imageView10);
        btnDecouvrons = findViewById(R.id.button); // Bouton pour découvrir

        // Liste des gouvernorats de la Tunisie
        final String[] gouvernorats = {
                "Ariana", "Beja", "Ben Arous", "Bizerte", "Gabes", "Gafsa", "Jendouba", "Kairouan",
                "Kasserine", "Kebili", "Kef", "Mahdia", "Manouba", "Medenine", "Monastir", "Nabeul",
                "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine", "Tozeur", "Tunis", "Zaghouan"
        };

        // Liste des types d'événements
        final String[] evenements = {
                "Théâtre", "Films", "Cirque"
        };

        // Afficher les gouvernorats lors du clic sur imageView8
        imageView8.setOnClickListener(v -> {
            new AlertDialog.Builder(ReservationActivity.this)
                    .setTitle("Choisissez un gouvernorat")
                    .setItems(gouvernorats, (dialog, which) -> {
                        editTextText6.setText(gouvernorats[which]);
                        Toast.makeText(ReservationActivity.this, "Gouvernorat sélectionné: " + gouvernorats[which], Toast.LENGTH_SHORT).show();
                    })
                    .show();
        });

        // Afficher les événements lors du clic sur imageView9
        imageView9.setOnClickListener(v -> {
            new AlertDialog.Builder(ReservationActivity.this)
                    .setTitle("Choisissez un événement")
                    .setItems(evenements, (dialog, which) -> {
                        editTextText8.setText(evenements[which]);
                        Toast.makeText(ReservationActivity.this, "Événement sélectionné: " + evenements[which], Toast.LENGTH_SHORT).show();
                    })
                    .show();
        });

        // Afficher le calendrier lors du clic sur imageView10
        imageView10.setOnClickListener(v -> {
            // Récupérer la date actuelle
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Créer un DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    ReservationActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        editTextText7.setText(date);
                    },
                    year, month, day
            );

            datePickerDialog.show();
        });

        // Redirection en fonction du genre sélectionné
        btnDecouvrons.setOnClickListener(v -> {
            String selectedGenre = editTextText8.getText().toString().trim();

            if (selectedGenre.equals("Films")) {
                startActivity(new Intent(ReservationActivity.this, Fims.class));
            } else if (selectedGenre.equals("Cirque")) {
                startActivity(new Intent(ReservationActivity.this, Cirque.class));
            } else if (selectedGenre.equals("Théâtre")) {
                startActivity(new Intent(ReservationActivity.this, Theatre.class));
            } else {
                Toast.makeText(ReservationActivity.this, "Veuillez sélectionner un genre valide", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
