package com.example.spectacle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class Cirque extends AppCompatActivity {
    private Map<Integer, Event> eventMap = new HashMap<>();
    private Map<Integer, String> urlMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cirque);

        // Initialisation des URLs YouTube
        urlMap.put(R.id.imageView14, "https://youtube.com/shorts/Oof9osktSSs?si=KyxlemY809Y_vQqB");
        urlMap.put(R.id.imageView17, "https://youtube.com/shorts/FQ_n5ZhP82U?si=if5nANZ1578enQXt");
        urlMap.put(R.id.imageView27, "https://youtube.com/shorts/j7PWxCSDgdM?si=sKqTmzSMmJL_ijfg");

        // Initialisation des événements
        eventMap.put(R.id.imageView14, new Event("Cirque Du Soleil", "2025/07/08", "16:30", "2.5 Heures", "Théâtre Municipal de Sfax ", R.drawable.img_11,200,"Cirque"));
        eventMap.put(R.id.imageView17, new Event("7festival", "2025/05/07", "20:00", "3 Heures", "Théâtre Municipal de Houmt Souk , Djerba", R.drawable.img_12,200,"Cirque"));
        eventMap.put(R.id.imageView27, new Event("Orfei", "2026/07/07", "21:00", "3 Heures", "Théâtre Municipal de Houmt Souk , Djerba", R.drawable.img_14,200,"Cirque"));

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
