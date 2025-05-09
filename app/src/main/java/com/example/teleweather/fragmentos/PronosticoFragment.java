package com.example.teleweather.fragmentos;

import android.app.AlertDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

import com.example.teleweather.adapter.PronosticoAdapter;
import com.example.teleweather.databinding.FragmentPronosticoBinding;
import com.example.teleweather.model.PronosticoResponse;
import com.example.teleweather.retrofit.ApiClient;
import com.example.teleweather.retrofit.ClimaApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/* para la parte de sensores: */
/* promt: en el uso de sensores, como puedo realizar la detección del "shake" del dispositivo?, la comparación con el umbral (20 m/s^2) */
/* Respuesta: apartado (a), (b), (c)*/
public class PronosticoFragment extends Fragment implements SensorEventListener {

    private FragmentPronosticoBinding binding;
    private final String API_KEY = "ec24b1c6dd8a4d528c1205500250305";

    // Sensor
    private SensorManager sensorManager;
    private Sensor acelerometro;
    private final float UMBRAL = 20f;

    // Esta lista representa los pronósticos cargados (a)
    private List<PronosticoResponse.ForecastDay> listaPronosticos = new ArrayList<>();
    private PronosticoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPronosticoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar sensor
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        Bundle args = getArguments();

        if (args != null && args.containsKey("id")) {
            int id = args.getInt("id");
            buscarPronostico("id:" + id, 14);
        }

        binding.btnBuscarPronostico.setOnClickListener(v -> {
            String idText = binding.etIdLocation.getText().toString().trim();
            String diasText = binding.etDias.getText().toString().trim();

            if (idText.isEmpty() || diasText.isEmpty()) {
                Toast.makeText(getContext(), "Completa ambos campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int id = Integer.parseInt(idText);
            int dias = Integer.parseInt(diasText);

            if (dias < 1 || dias > 14) {
                Toast.makeText(getContext(), "Máximo 14 días", Toast.LENGTH_SHORT).show();
                return;
            }

            buscarPronostico("id:" + id, dias);
        });
    }

    private void buscarPronostico(String query, int dias) {
        ClimaApiService apiService = ApiClient.getRetrofit().create(ClimaApiService.class);
        apiService.obtenerPronostico(API_KEY, query, dias).enqueue(new Callback<PronosticoResponse>() {
            @Override
            public void onResponse(Call<PronosticoResponse> call, Response<PronosticoResponse> response) {
                if (response.isSuccessful()) {
                    listaPronosticos = response.body().forecast.forecastday;
                    adapter = new PronosticoAdapter(listaPronosticos, getContext());
                    binding.rvPronostico.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.rvPronostico.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<PronosticoResponse> call, Throwable t) {
                Log.e("api-error", "Error: " + t.getMessage());
            }
        });
    }

    // Activar sensor al reanudar
    @Override
    public void onResume() {
        super.onResume();
        if (acelerometro != null) {
            sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    // Desactivar sensor al pausar
    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    // Detectar agitación fuerte (b)
    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        double aceleracion = Math.sqrt(x * x + y * y + z * z);

        if (aceleracion > UMBRAL && !listaPronosticos.isEmpty()) {
            sensorManager.unregisterListener(this); // para evitar repetición

            new AlertDialog.Builder(requireContext())
                    .setTitle("¿Eliminar pronósticos?")
                    .setMessage("Se detectó una agitación. ¿Deseas borrar la lista?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        listaPronosticos.clear();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Pronósticos eliminados", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancelar", (dialog, which) -> {
                        // Reactivar sensor si el usuario cancela (c)
                        sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
                    })
                    .setCancelable(false)
                    .show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
