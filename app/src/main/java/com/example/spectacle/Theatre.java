package com.example.spectacle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;

public class Theatre extends AppCompatActivity {

    private Map<Integer, Event> eventMap = new HashMap<>();
    private Map<Integer, String> urlMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_theatre);
        urlMap.put(R.id.imageView15, "https://www.facebook.com/WajihaJendoubi24sur20/?locale=fr_FR");
        urlMap.put(R.id.imageView16, "https://www.tekiano.com/2020/02/12/mamou-chehyma-la-nouvelle-piece-de-kamel-touati-sleh-msadek-et-lassaad-ben-abdallah-au-theatre-de-tunis/");
        urlMap.put(R.id.imageView18, "https://www.jetsetmagazine.net/FR.6.culture.theatre_et_dance.nmout_3alik_de_lamine_nahdi.1580");

        // Initialisation des événements
        eventMap.put(R.id.imageView15, new Event("Big Bossa", "2025/05/20", "14:55", "2 Heures", "Théâtre Municipal de Tunis  ", R.drawable.img_15,200,"Théâtre"));
        eventMap.put(R.id.imageView16, new Event("Mamou & chehyma", "2025-05-07", "20:00", "1.5 Heures", "Théâtre Municipal de Sousse", R.drawable.img_16,200,"Théâtre"));
        eventMap.put(R.id.imageView18, new Event("نموت عليك", "2025/12/31", "20:00", "2 Heures", "héâtre Municipal de Sousse ", R.drawable.img_10,200,"Théâtre"));

        // Configuration des écouteurs pour chaque ImageView
        for (int imageViewId : eventMap.keySet()) {
            ImageView imageView = findViewById(imageViewId);

            if (imageView != null) { // Vérifie que l'image existe dans le layout
                // Clic normal → ouvre YouTube si un lien existe
                imageView.setOnClickListener(view -> {
                    String url = urlMap.get(imageViewId);
                    if (url != null) {
                        openLink(url);
                    } else {
                        Log.d("DEBUG", "Aucun lien pour l'image " + imageViewId);
                    }
                });

                // Appui long → ouvre EventDetailActivity
                imageView.setOnLongClickListener(view -> {
                    openDetailActivity(eventMap.get(imageViewId));
                    return true; // Empêche l'action normale après le long clic
                });
            } else {
                Log.e("ERROR", "ImageView non trouvé pour ID : " + imageViewId);
            }
        }
    }

    private void openLink(String url) {
        if (url != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } else {
            Log.e("ERROR", "URL null, impossible d'ouvrir le lien !");
        }
    }

    private void openDetailActivity(Event event) {
        if (event != null) {
            Intent intent = new Intent(this, EventDetailActivity.class);
            intent.putExtra("event", event);
            startActivity(intent);
        } else {
            Log.e("ERROR", "L'événement est null, impossible d'ouvrir EventDetailActivity !");
        }
    }
}
