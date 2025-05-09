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
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.teleweather.databinding.FragmentLocationBinding;
import com.example.teleweather.model.Location;
import com.example.teleweather.retrofit.ApiClient;
import com.example.teleweather.retrofit.ClimaApiService;
import com.example.teleweather.adapter.LocationAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationFragment extends Fragment {

    private FragmentLocationBinding binding;
    private ClimaApiService apiService;
    private final String API_KEY = "ec24b1c6dd8a4d528c1205500250305";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLocationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiService = ApiClient.getRetrofit().create(ClimaApiService.class);
        binding.rvLocations.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.btnBuscar.setOnClickListener(v -> {
            String query = binding.etBuscar.getText().toString().trim();
            if (!query.isEmpty()) {
                buscarLocacion(query);
            }
        });
    }

    private void buscarLocacion(String query) {
        apiService.buscarLocacion(API_KEY, query).enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                if (response.isSuccessful()) {
                    List<Location> resultados = response.body();

                    LocationAdapter adapter = new LocationAdapter(resultados, getContext());

                    /*Promt: sé que utilizando safe args puedo pasar parámetros de forma segura para navegar, cómo lo utilizo para volver de Pronóstico a Locación sin problemas en la navegación?*/
                    adapter.setOnItemClickListener(loc -> {
                        NavController navController = NavHostFragment.findNavController(LocationFragment.this);
                        navController.navigate(
                                LocationFragmentDirections
                                        .actionLocationFragmentToPronosticoFragment(loc.getId())
                        );
                    });

                    binding.rvLocations.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                Log.e("error", "Falla en conexión: " + t.getMessage());
            }
        });
    }
}
