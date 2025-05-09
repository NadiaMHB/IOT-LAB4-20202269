package com.example.teleweather;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.teleweather.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (hayInternet()) {
            binding.btnIngresar.setEnabled(true);
            binding.btnIngresar.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, AppActivity.class);
                startActivity(intent);
            });
        } else {
            binding.btnIngresar.setEnabled(false);
            mostrarDialogoSinInternet();
        }
    }


    private boolean hayInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected());
        }
        return false;
    }

    private void mostrarDialogoSinInternet() {
        new AlertDialog.Builder(this)
                .setTitle("Sin conexión")
                .setMessage("Por favor active su conexión a Internet.")
                .setPositiveButton("Configuración", (dialog, which) -> {
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                })
                .setCancelable(false)
                .show();
    }
}
