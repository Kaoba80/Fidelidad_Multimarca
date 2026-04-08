package com.example.fidelidadmultimarca

import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class EditarPerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)

        val btnCancelar = findViewById<MaterialButton>(R.id.btnCancelarEditarPerfil)
        val btnGuardar = findViewById<MaterialButton>(R.id.btnGuardarPerfil)

        btnCancelar.setOnClickListener {
            // RESPUESTA HÁPTICA (Vibración al tocar)
            it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)

            Toast.makeText(this, "Cambios cancelados", Toast.LENGTH_SHORT).show()
            finish() // Cierra la pantalla y vuelve a Ajustes
        }

        btnGuardar.setOnClickListener {
            // RESPUESTA HÁPTICA (Vibración al tocar)
            it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)

            Toast.makeText(this, "Cambios guardados correctamente", Toast.LENGTH_SHORT).show()
            finish() // Cierra la pantalla y vuelve a Ajustes
        }
    }
}