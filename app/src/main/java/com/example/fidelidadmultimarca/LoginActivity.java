package com.example.fidelidadmultimarca;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 1. Iniciar la animación de fondo
        ConstraintLayout layoutPrincipal = findViewById(R.id.layoutPrincipal);
        AnimationDrawable animationDrawable = (AnimationDrawable) layoutPrincipal.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        // 2. Enlazar los botones y textos clickeables
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvRegistro = findViewById(R.id.tvRegistro);
        TextView tvOlvidePassword = findViewById(R.id.tvOlvidePassword);

        // 3. Botón "Iniciar Sesión": Va a la pantalla principal (Dashboard) y cierra el Login
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Cierra el login para que el usuario no vuelva atrás al pulsar el botón del móvil
        });

        // 4. Texto "Olvidé la Contraseña": Va a la pantalla de recuperación
        tvOlvidePassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, OlvidePasswordActivity.class);
            startActivity(intent);
        });

        // 5. Texto "Registro": Va a la pantalla de crear cuenta
        tvRegistro.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivity(intent);
        });
    }
}