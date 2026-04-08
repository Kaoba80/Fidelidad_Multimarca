package com.example.fidelidadmultimarca;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class OlvidePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvide_password);

        // Iniciar la animación de fondo
        ConstraintLayout layoutOlvidePassword = findViewById(R.id.layoutOlvidePassword);
        AnimationDrawable animationDrawable = (AnimationDrawable) layoutOlvidePassword.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        Button btnEnviarInstrucciones = findViewById(R.id.btnEnviarInstrucciones);
        TextView tvVolverDesdeOlvide = findViewById(R.id.tvVolverDesdeOlvide);

        // Simular el envío de correo y volver
        btnEnviarInstrucciones.setOnClickListener(v -> {
            Toast.makeText(this, "Instrucciones enviadas al correo", Toast.LENGTH_LONG).show();
            finish(); // Cierra esta pantalla y vuelve al Login
        });

        // Botón de texto para volver atrás
        tvVolverDesdeOlvide.setOnClickListener(v -> finish());
    }
}