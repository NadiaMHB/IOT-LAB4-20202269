package com.example.teleweather.fragmentos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.teleweather.adapter.DeporteAdapter;
import com.example.teleweather.databinding.FragmentDeportesBinding;
import com.example.teleweather.model.Deportes;
import com.example.teleweather.model.SportsResponse;
import com.example.teleweather.retrofit.ApiClient;
import com.example.teleweather.retrofit.ClimaApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeportesFragment extends Fragment {

    private FragmentDeportesBinding binding;
    private final String API_KEY = "ec24b1c6dd8a4d528c1205500250305";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDeportesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnBuscar.setOnClickListener(v -> {
            String lugar = binding.etLugar.getText().toString().trim();
            if (lugar.isEmpty()) {
                Toast.makeText(getContext(), "Ingrese un lugar", Toast.LENGTH_SHORT).show();
                return;
            }

            /* Promt: como llamo a la API usando retrofit con enqueue adaptándolo al API Weather Api? */
            ClimaApiService apiService = ApiClient.getRetrofit().create(ClimaApiService.class);
            apiService.obtenerEventosDeportivos(API_KEY, lugar).enqueue(new Callback<SportsResponse>() {
                @Override
                public void onResponse(Call<SportsResponse> call, Response<SportsResponse> response) {
                    if (response.isSuccessful()) {
                        List<Deportes> lista = response.body().football;
                        DeporteAdapter adapter = new DeporteAdapter(lista, getContext());
                        binding.rvDeportes.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.rvDeportes.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<SportsResponse> call, Throwable t) {
                    Log.e("api-deportes", "Falla: " + t.getMessage());
                }
            });
        });
    }
}
