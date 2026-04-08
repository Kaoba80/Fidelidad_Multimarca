package com.example.fidelidadmultimarca;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class RegistroActivity extends AppCompatActivity {

    private CheckBox cbCondiciones;

    // Este "Lanzador" abre la pantalla de condiciones y escucha qué pasa al volver
    private final ActivityResultLauncher<Intent> condicionesLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // Si el usuario aceptó en la otra pantalla, habilitamos y marcamos el checkbox
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

        // Configurar las opciones del desplegable (DNI, NIE, Pasaporte)
        AutoCompleteTextView spinnerTipoDoc = findViewById(R.id.spinnerTipoDoc);
        String[] tiposDoc = getResources().getStringArray(R.array.tipos_documento);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, tiposDoc);
        spinnerTipoDoc.setAdapter(adapter);

        // Enlazar vistas de navegación
        TextView tvVolverLogin = findViewById(R.id.tvVolverLogin);
        TextView tvLeerCondiciones = findViewById(R.id.tvLeerCondiciones);
        cbCondiciones = findViewById(R.id.cbCondiciones);
        Button btnRegistrarse = findViewById(R.id.btnRegistrarse);

        // Botón volver al Login (finish destruye esta pantalla y revela el login que estaba debajo)
        tvVolverLogin.setOnClickListener(v -> finish());

        // Botón de Registrarse (por ahora solo volvemos al login simulando que terminó)
        btnRegistrarse.setOnClickListener(v -> finish());

        // Texto para abrir las Condiciones usando el lanzador que creamos arriba
        tvLeerCondiciones.setOnClickListener(v -> {
            Intent intent = new Intent(RegistroActivity.this, CondicionesActivity.class);
            condicionesLauncher.launch(intent);
        });
    }
}