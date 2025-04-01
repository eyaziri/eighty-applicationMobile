package com.example.spectacle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.HashMap;
import java.util.Map;

public class Fims extends AppCompatActivity {
    private Map<Integer, Event> eventMap = new HashMap<>();
    private Map<Integer, String> urlMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fims);

        // Gestion des marges pour éviter le chevauchement avec la barre de statut
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Association des liens YouTube aux images
        urlMap.put(R.id.imageView7, "https://youtu.be/f2FUiq2nZAo?si=R434CU5jC9mRq2lG");
        urlMap.put(R.id.imageView11, "https://youtu.be/txwrLGAL7Sg?si=vGpT5z6kAUHpbUdh");
        urlMap.put(R.id.imageView12, "https://youtu.be/husMGbXEIho?si=QaT7-lvvieKbK48d");
        urlMap.put(R.id.imageView13, "https://youtu.be/kpps_kn2PNM?si=0NC3wbahsLibb-Zz");

        // Création des objets Event
        eventMap.put(R.id.imageView7, new Event("The Insider", "2025/07/30", "18:00", "2 Heures", "Pathé Tunis City", R.drawable.img_3,200,"Films"));
        eventMap.put(R.id.imageView11, new Event("Nefarious", "2025/05/30", "20:00", "2 Heures", "Pathé Tunis City", R.drawable.img_4,200,"Films"));
        eventMap.put(R.id.imageView12, new Event("The Monkey", "2025/05/20", "19:30", "2 Heures", "Pathé Tunis City", R.drawable.img_5,200,"Films"));
        eventMap.put(R.id.imageView13, new Event("Drop Game", "2025/06/20", "21:00", "2 Heures", "Pathé Tunis City", R.drawable.img_6,200,"Films"));

        // Appliquer les écouteurs pour chaque image
        for (Map.Entry<Integer, Event> entry : eventMap.entrySet()) {
            int imageViewId = entry.getKey();
            ImageView imageView = findViewById(imageViewId);

            imageView.setOnClickListener(view -> {
                if (view.getId() == imageViewId) {
                    openLink(urlMap.get(imageViewId)); // Ouvrir le lien YouTube
                }
            });

            imageView.setOnLongClickListener(view -> {
                openDetailActivity(entry.getValue()); // Ouvrir EventDetailActivity en appuyant longtemps
                return true; // Empêche l'action normale après le long clic
            });
        }
    }

    // Fonction pour ouvrir un lien YouTube
    private void openLink(String url) {
        if (url != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        }
    }

    // Ouvrir l'activité de détail avec un objet Event
    private void openDetailActivity(Event event) {
        Intent intent = new Intent(this, EventDetailActivity.class);
        intent.putExtra("event", event);  // Passer l'objet Event
        startActivity(intent);
    }
}