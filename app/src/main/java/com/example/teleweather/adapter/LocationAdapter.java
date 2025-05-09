package com.example.teleweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teleweather.R;
import com.example.teleweather.model.Location;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private List<Location> lista;
    private Context context;

    public LocationAdapter(List<Location> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Location loc = lista.get(position);

        holder.tvId.setText("Id: " +String.valueOf(loc.id));
        holder.tvNombre.setText("Nombre: " +loc.name);
        holder.tvRegion.setText("Region: " +loc.region);
        holder.tvPais.setText("Pais: " +loc.country);
        holder.tvLatitud.setText("Latitud: " +String.valueOf(loc.lat));
        holder.tvLongitud.setText("Longitud: " +String.valueOf(loc.lon));

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(loc);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvNombre, tvRegion, tvPais, tvLatitud, tvLongitud;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvRegion = itemView.findViewById(R.id.tvRegion);
            tvPais = itemView.findViewById(R.id.tvPais);
            tvLatitud = itemView.findViewById(R.id.tvLatitud);
            tvLongitud = itemView.findViewById(R.id.tvLongitud);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Location location);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
