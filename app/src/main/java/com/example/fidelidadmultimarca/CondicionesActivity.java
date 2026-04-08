package com.example.fidelidadmultimarca;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class CondicionesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condiciones);

        Button btnAceptarCondiciones = findViewById(R.id.btnAceptarCondiciones);

        btnAceptarCondiciones.setOnClickListener(v -> {
            // Mandamos una señal de que todo ha ido bien (RESULT_OK)
            setResult(RESULT_OK);
            // Cerramos la pantalla
            finish();
        });
    }
}