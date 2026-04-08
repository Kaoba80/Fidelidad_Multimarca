package com.example.fidelidadmultimarca

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ChatAdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_admin)

        val btnVolver = findViewById<ImageButton>(R.id.btnVolverChat)
        val btnEnviar = findViewById<ImageButton>(R.id.btnEnviarMensaje)
        val etMensaje = findViewById<EditText>(R.id.etMensaje)

        btnVolver.setOnClickListener {
            finish()
        }

        btnEnviar.setOnClickListener {
            val texto = etMensaje.text.toString()
            if (texto.isNotEmpty()) {
                // Simula que lo envía y vacía el recuadro
                Toast.makeText(this, "Mensaje enviado", Toast.LENGTH_SHORT).show()
                etMensaje.text.clear()
            }
        }
    }
}