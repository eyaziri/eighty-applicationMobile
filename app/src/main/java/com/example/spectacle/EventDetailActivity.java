package com.example.spectacle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EventDetailActivity extends AppCompatActivity {

    private TextView title, date, time, duration, location;
    private ImageView imageView;
    private Button btnReserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        // Lier les composants UI
        title = findViewById(R.id.title);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        duration = findViewById(R.id.duration);
        location = findViewById(R.id.location);
        imageView = findViewById(R.id.image);
        btnReserve = findViewById(R.id.btnReserve);

        // Récupérer l'objet Event passé via Intent
        Event event = (Event) getIntent().getSerializableExtra("event");

        // Vérifier si l'objet event est bien reçu et afficher ses données
        if (event != null) {
            title.setText(event.getTitle());
            date.setText(event.getDate());
            time.setText(event.getTime());
            duration.setText(event.getDuration());
            location.setText(event.getLocation());
            imageView.setImageResource(event.getImageResId());

        }

        // Gérer le clic sur le bouton de réservation
        btnReserve.setOnClickListener(v -> {
            Intent intent = new Intent(EventDetailActivity.this, Coordonnees.class);
            intent.putExtra("event", event);
            startActivity(intent);
        });


        // Ajuster les marges pour éviter le chevauchement avec la barre de statut
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
