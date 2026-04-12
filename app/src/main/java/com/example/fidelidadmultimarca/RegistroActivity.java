package com.example.fidelidadmultimarca;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class RegistroActivity extends AppCompatActivity {

    private CheckBox cbCondiciones;

    private final ActivityResultLauncher<Intent> condicionesLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    cbCondiciones.setEnabled(true);
                    cbCondiciones.setChecked(true);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Iniciar la animación de fondo
        ConstraintLayout layoutRegistro = findViewById(R.id.layoutRegistro);
        AnimationDrawable animationDrawable = (AnimationDrawable) layoutRegistro.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        // Enlazar vistas de navegación y control
        TextView tvVolverLogin = findViewById(R.id.tvVolverLogin);
        TextView tvLeerCondiciones = findViewById(R.id.tvLeerCondiciones);
        cbCondiciones = findViewById(R.id.cbCondiciones);
        Button btnRegistrarse = findViewById(R.id.btnRegistrarse);

        // Botón volver al Login
        tvVolverLogin.setOnClickListener(v -> finish());

        // Botón de Registrarse
        btnRegistrarse.setOnClickListener(v -> finish());

        // Texto para abrir las Condiciones
        tvLeerCondiciones.setOnClickListener(v -> {
            Intent intent = new Intent(RegistroActivity.this, CondicionesActivity.class);
            condicionesLauncher.launch(intent);
        });
    }
}