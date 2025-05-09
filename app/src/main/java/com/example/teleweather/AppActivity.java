package com.example.teleweather;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.teleweather.databinding.ActivityAppBinding;

public class AppActivity extends AppCompatActivity {

    private ActivityAppBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(binding.bottomNav, navController);
    }

    /*Promp: como soluciono el backstack para forzar del cierre de mi actividad al presionar atrás si estoy en el fragmento raíz? */

    /* Respuesta: Sobrescribe onBackPressed() para salir de AppActivity: */
    @Override
    public void onBackPressed() {
        int currentDestId = navController.getCurrentDestination().getId();

        if (currentDestId == R.id.locationFragment
                || currentDestId == R.id.pronosticoFragment
                || currentDestId == R.id.deportesFragment) {
            // Cierra AppActivity y vuelve a MainActivity
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
