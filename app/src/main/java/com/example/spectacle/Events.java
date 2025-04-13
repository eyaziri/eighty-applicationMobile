package com.example.spectacle;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Events extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_events);

        // Gestion des Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerViewEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ✅ Ajout de l'espacement entre les items
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);
        recyclerView.addItemDecoration(new SpacingItemDecoration(spacingInPixels));

        String selectedGenre = getIntent().getStringExtra("selectedGenre");

        if (selectedGenre != null && !selectedGenre.isEmpty()) {
            getSpectaclesFromApi(selectedGenre);
        } else {
            Toast.makeText(this, "Aucun genre sélectionné", Toast.LENGTH_SHORT).show();
        }
    }

    private void getSpectaclesFromApi(String genre) {
        ApiService apiService = RetrofitClient.getApiService();

        Call<List<Spectacle>> call = apiService.getSpectacles();

        call.enqueue(new Callback<List<Spectacle>>() {
            @Override
            public void onResponse(Call<List<Spectacle>> call, Response<List<Spectacle>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Spectacle> spectacles = response.body();
                    List<Spectacle> filteredSpectacles = filterSpectaclesByGenre(spectacles, genre);

                    if (!filteredSpectacles.isEmpty()) {
                        adapter = new EventAdapter(Events.this, filteredSpectacles);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(Events.this, "Aucun événement correspondant au genre", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Events.this,
                            "Erreur : " + response.code() + " - " + response.message(),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Spectacle>> call, Throwable t) {
                Toast.makeText(Events.this, "Échec de la connexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Spectacle> filterSpectaclesByGenre(List<Spectacle> spectacles, String genre) {
        List<Spectacle> filteredSpectacles = new ArrayList<>();
        for (Spectacle spectacle : spectacles) {
            if (spectacle.getGenre() != null && spectacle.getGenre().equalsIgnoreCase(genre)) {
                filteredSpectacles.add(spectacle);
            }
        }
        return filteredSpectacles;
    }
}
