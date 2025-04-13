package com.example.spectacle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailActivity extends AppCompatActivity {

    private TextView title, date, time, location, duration;
    private ImageView imageView;
    private Button btnReserve;
    private int eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        // Bind UI components
        title = findViewById(R.id.title);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        location = findViewById(R.id.location);
        imageView = findViewById(R.id.image);
        btnReserve = findViewById(R.id.btnReserve);
        duration=findViewById(R.id.duration);

        // Get the eventId passed from the previous activity
        eventId = getIntent().getIntExtra("eventId", -1);

        if (eventId != -1) {
            // Fetch the event details from the API using the eventId
            fetchEventDetails(eventId);
        } else {
            Toast.makeText(this, "Event not found", Toast.LENGTH_SHORT).show();
        }

        // Handle the reserve button click (if any functionality is needed)
        btnReserve.setOnClickListener(v -> {
            Intent intent = new Intent(EventDetailActivity.this, Coordonnees.class);
            intent.putExtra("eventId", eventId);
            startActivity(intent);
        });

        // Adjust padding to avoid overlap with system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void fetchEventDetails(int eventId) {
        ApiService apiService = RetrofitClient.getApiService();

        // Make the API call to get the event details by eventId
        Call<Spectacle> call = apiService.getEventById(eventId);
        call.enqueue(new Callback<Spectacle>() {
            @Override
            public void onResponse(Call<Spectacle> call, Response<Spectacle> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Spectacle event = response.body();

                    // Set the details into the UI
                    title.setText(event.getTitre());
                    date.setText(event.getDateS());
                    time.setText(event.getH_debut());
                    location.setText(event.getIdLieu());
                    duration.setText((event.getDureeS()));// Customize further as needed

                    // Load the image from resources (local images in drawable)
                    int imageResId = getResources().getIdentifier(
                            event.getUrlImage(),
                            "drawable",
                            getPackageName()
                    );

                    if (imageResId != 0) {
                        imageView.setImageResource(imageResId);
                    } else {
                        imageView.setImageResource(R.drawable.img_1); // Default image if not found
                    }

                    // You can handle the trailer link here if you want to open it directly.
                    String trailerUrl = event.getUrlTrailer();
                    imageView.setOnClickListener(v -> {
                        if (trailerUrl != null && !trailerUrl.isEmpty()) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
                            startActivity(browserIntent);
                        } else {
                            Toast.makeText(EventDetailActivity.this, "Trailer URL is not available", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(EventDetailActivity.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Spectacle> call, Throwable t) {
                Toast.makeText(EventDetailActivity.this, "Failed to load event details", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
