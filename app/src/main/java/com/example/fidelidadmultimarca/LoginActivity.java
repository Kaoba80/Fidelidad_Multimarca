package com.example.fidelidadmultimarca;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etUsuario, etPassword;
    private FirebaseAuth mAuth; // Instancia de Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // 1. Iniciar la animación de fondo
        ConstraintLayout layoutPrincipal = findViewById(R.id.layoutPrincipal);
        AnimationDrawable animationDrawable = (AnimationDrawable) layoutPrincipal.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        // 2. Enlazar vistas
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvRegistro = findViewById(R.id.tvRegistro);
        TextView tvOlvidePassword = findViewById(R.id.tvOlvidePassword);
        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etPassword);

        // 3. Botón "Iniciar Sesión"
        btnLogin.setOnClickListener(v -> iniciarSesion());

        // 4. Texto "Olvidé la Contraseña"
        tvOlvidePassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, OlvidePasswordActivity.class);
            startActivity(intent);
        });

        // 5. Texto "Registro"
        tvRegistro.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivity(intent);
        });
    }

    // Comprobar si el usuario ya está logueado al abrir la app
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Si ya hay sesión iniciada, saltar directamente al MainActivity
            irAMainActivity();
        }
    }

    private void iniciarSesion() {
        String email = etUsuario.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validaciones básicas antes de enviar a Firebase
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etUsuario.setError("Introduce un correo válido");
            etUsuario.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Introduce tu contraseña");
            etPassword.requestFocus();
            return;
        }

        // Iniciar sesión con Firebase
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Éxito al iniciar sesión
                        irAMainActivity();
                    } else {
                        // Fallo al iniciar sesión (contraseña incorrecta o correo no registrado)
                        Toast.makeText(LoginActivity.this, "Autenticación fallida. Revisa tus credenciales.", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void irAMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Cierra el login para que el usuario no vuelva atrás
    }
}