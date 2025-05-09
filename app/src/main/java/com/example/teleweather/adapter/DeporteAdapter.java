package com.example.teleweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teleweather.R;
import com.example.teleweather.model.Deportes;

import java.util.List;

public class DeporteAdapter extends RecyclerView.Adapter<DeporteAdapter.ViewHolder> {

    private List<Deportes> lista;
    private Context context;

    public DeporteAdapter(List<Deportes> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.item_deporte, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Deportes match = lista.get(position);
        holder.tvEstadio.setText("Estadio: " + match.stadium);
        holder.tvPais.setText("País: " + match.country);
        holder.tvTorneo.setText("Torneo: " + match.tournament);
        holder.tvInicio.setText("Inicio: " + match.start);
        holder.tvPartido.setText("Partido: " + match.match);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEstadio, tvPais, tvTorneo, tvInicio, tvPartido;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEstadio = itemView.findViewById(R.id.tvEstadio);
            tvPais = itemView.findViewById(R.id.tvPais);
            tvTorneo = itemView.findViewById(R.id.tvTorneo);
            tvInicio = itemView.findViewById(R.id.tvInicio);
            tvPartido = itemView.findViewById(R.id.tvPartido);
        }
    }
}
