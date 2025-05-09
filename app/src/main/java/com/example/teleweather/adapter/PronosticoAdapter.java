package com.example.teleweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teleweather.R;
import com.example.teleweather.model.PronosticoResponse;

import java.util.List;

public class PronosticoAdapter extends RecyclerView.Adapter<PronosticoAdapter.ViewHolder> {

    private List<PronosticoResponse.ForecastDay> lista;
    private Context context;

    public PronosticoAdapter(List<PronosticoResponse.ForecastDay> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.item_pronostico, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PronosticoResponse.ForecastDay dia = lista.get(position);

        holder.tvFecha.setText(dia.date);
        holder.tvTempMax.setText("Temperatura máxima: " + dia.day.maxtemp_c + "°C");
        holder.tvTempMin.setText("Temperatura mínima: " + dia.day.mintemp_c + "°C");
        holder.tvCondicion.setText("Condición: " + dia.day.condition.text);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFecha, tvTempMax, tvTempMin, tvCondicion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvTempMax = itemView.findViewById(R.id.tvTempMax);
            tvTempMin = itemView.findViewById(R.id.tvTempMin);
            tvCondicion = itemView.findViewById(R.id.tvCondicion);
        }
    }
}
