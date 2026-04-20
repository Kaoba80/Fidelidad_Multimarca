package com.example.fidelidadmultimarca;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {

    private CheckBox cbCondiciones;
    private TextInputEditText etEmailRegistro, etPasswordRegistro, etPasswordConfirmar;
    private FirebaseAuth mAuth; // Instancia de Firebase

    // Expresión regular para: Al menos 1 número, 1 minúscula, 1 mayúscula, 1 carácter especial y mínimo 6 caracteres
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         // al menos un número
                    "(?=.*[a-z])" +         // al menos una letra minúscula
                    "(?=.*[A-Z])" +         // al menos una letra mayúscula
                    "(?=.*[.@#$%^&+=!_\\-])" + // al menos un carácter especial
                    "(?=\\S+$)" +           // sin espacios en blanco
                    ".{6,}" +               // al menos 6 caracteres
                    "$");

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

        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Iniciar la animación de fondo
        ConstraintLayout layoutRegistro = findViewById(R.id.layoutRegistro);
        AnimationDrawable animationDrawable = (AnimationDrawable) layoutRegistro.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        // Enlazar vistas
        TextView tvVolverLogin = findViewById(R.id.tvVolverLogin);
        TextView tvLeerCondiciones = findViewById(R.id.tvLeerCondiciones);
        cbCondiciones = findViewById(R.id.cbCondiciones);
        Button btnRegistrarse = findViewById(R.id.btnRegistrarse);

        etEmailRegistro = findViewById(R.id.etEmailRegistro);
        etPasswordRegistro = findViewById(R.id.etPasswordRegistro);
        etPasswordConfirmar = findViewById(R.id.etPasswordConfirmar);

        // Botón volver al Login
        tvVolverLogin.setOnClickListener(v -> finish());

        // Texto para abrir las Condiciones
        tvLeerCondiciones.setOnClickListener(v -> {
            Intent intent = new Intent(RegistroActivity.this, CondicionesActivity.class);
            condicionesLauncher.launch(intent);
        });

        // Lógica del botón de Registrarse
        btnRegistrarse.setOnClickListener(v -> registrarUsuario());
    }

    private void registrarUsuario() {
        String email = etEmailRegistro.getText().toString().trim();
        String password = etPasswordRegistro.getText().toString().trim();
        String confirmPassword = etPasswordConfirmar.getText().toString().trim();

        // 1. Validar Email (Que no esté vacío, contenga @ y . y sea un formato válido)
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() || !email.contains("@") || !email.contains(".")) {
            etEmailRegistro.setError("Por favor, introduce un correo válido");
            etEmailRegistro.requestFocus();
            return;
        }

        // 2. Validar Contraseña (Mayúscula, símbolo, número y longitud)
        if (password.isEmpty() || !PASSWORD_PATTERN.matcher(password).matches()) {
            etPasswordRegistro.setError("Debe tener al menos 6 caracteres, una mayúscula, un número y un símbolo");
            etPasswordRegistro.requestFocus();
            return;
        }

        // 3. Validar que las contraseñas coincidan
        if (!password.equals(confirmPassword)) {
            etPasswordConfirmar.setError("Las contraseñas no coinciden");
            etPasswordConfirmar.requestFocus();
            return;
        }

        // 4. Validar Términos y Condiciones
        if (!cbCondiciones.isChecked()) {
            Toast.makeText(this, "Debes aceptar los Términos y Condiciones", Toast.LENGTH_SHORT).show();
            return;
        }

        // 5. Crear usuario en Firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Éxito al registrar
                        Toast.makeText(RegistroActivity.this, "Cuenta creada exitosamente", Toast.LENGTH_SHORT).show();
                        finish(); // Cierra el registro y vuelve al Login
                    } else {
                        // Fallo al registrar (ej. el correo ya existe)
                        Toast.makeText(RegistroActivity.this, "Error al crear la cuenta: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}