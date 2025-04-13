package com.example.spectacle;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Spectacle> spectacleList;
    private Context context;

    public EventAdapter(Context context, List<Spectacle> spectacleList) {
        this.context = context;
        this.spectacleList = spectacleList;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        Spectacle spectacle = spectacleList.get(position);
        holder.titre.setText(spectacle.getTitre());
        holder.date.setText(spectacle.getDateS());
        holder.time.setText(spectacle.getH_debut());
        holder.lieu.setText(spectacle.getIdLieu());

        // Load image from res/drawable with the name (e.g., "img_10")
        int imageResId = context.getResources().getIdentifier(
                spectacle.getUrlImage(), // Example: "img_10"
                "drawable",
                context.getPackageName()
        );

        if (imageResId != 0) {
            holder.image.setImageResource(imageResId);
        } else {
            holder.image.setImageResource(R.drawable.img_1); // Default image if not found
        }

        // ✅ Open URL on image click
        holder.image.setOnClickListener(v -> {
            String url = spectacle.getUrlTrailer();
            if (url != null && !url.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "No trailer link available for this event", Toast.LENGTH_SHORT).show();
            }
        });

        // ✅ Long click on image to open EventDetailActivity
        holder.image.setOnLongClickListener(v -> {
            Intent intent = new Intent(context, EventDetailActivity.class);
            intent.putExtra("eventId", spectacle.getIdSpec()); // Pass the idSpec to the detail activity
            context.startActivity(intent);
            return true; // Return true to indicate that the event is consumed
        });
    }

    @Override
    public int getItemCount() {
        return spectacleList.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        TextView titre, date, time, lieu;
        ImageView image;

        public EventViewHolder(View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.textTitre);
            date = itemView.findViewById(R.id.textDate);
            time = itemView.findViewById(R.id.textHeure);
            lieu = itemView.findViewById(R.id.textLieu);
            image = itemView.findViewById(R.id.imageSpectacle);
        }
    }
}
