package com.example.fidelidadmultimarca

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AjustesFragment : Fragment() {

    // Correo simulado del usuario (luego vendrá de la base de datos)
    private val correoUsuarioActual = "usuario@fidelidad.com"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ajustes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Enlazar vistas
        val tvEditarPerfil = view.findViewById<TextView>(R.id.tvEditarPerfil)
        val cardChatAdmin = view.findViewById<MaterialCardView>(R.id.cardChatAdmin)
        val btnEliminarCuenta = view.findViewById<MaterialButton>(R.id.btnEliminarCuenta)

        val tvTerminos = view.findViewById<TextView>(R.id.tvTerminos)
        val tvAvisoLegal = view.findViewById<TextView>(R.id.tvAvisoLegal)
        val tvFaq = view.findViewById<TextView>(R.id.tvFaq)

        // 1. Editar Perfil
        tvEditarPerfil.setOnClickListener {
            val intent = Intent(requireContext(), EditarPerfilActivity::class.java)
            startActivity(intent)
        }

        // 2. Chat con Admin
        cardChatAdmin.setOnClickListener {
            val intent = Intent(requireContext(), ChatAdminActivity::class.java)
            startActivity(intent)
        }

        // 3. Legales (Vinculado a la que ya teníamos)
        tvTerminos.setOnClickListener {
            val intent = Intent(requireContext(), CondicionesActivity::class.java)
            startActivity(intent)
        }
        tvAvisoLegal.setOnClickListener {
            Toast.makeText(requireContext(), "Abriendo Aviso Legal...", Toast.LENGTH_SHORT).show()
        }
        tvFaq.setOnClickListener {
            Toast.makeText(requireContext(), "Abriendo Preguntas Frecuentes...", Toast.LENGTH_SHORT).show()
        }

        // 4. Lógica de ELIMINAR CUENTA con confirmación de correo
        btnEliminarCuenta.setOnClickListener {
            mostrarDialogoEliminarCuenta()
        }
    }

    private fun mostrarDialogoEliminarCuenta() {
        // Crear un EditText por código para que el usuario escriba su correo
        val input = EditText(requireContext())
        input.hint = "Escribe tu correo aquí"
        input.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

        // Darle un poco de margen al EditText
        val layout = LinearLayout(requireContext())
        layout.setPadding(50, 20, 50, 0)
        layout.addView(input, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("¿Eliminar Cuenta?")
            .setMessage("Esta acción es irreversible. Para confirmar que eres tú, escribe tu correo electrónico ($correoUsuarioActual):")
            .setView(layout)
            .setPositiveButton("ELIMINAR") { dialog, _ ->
                val textoEscrito = input.text.toString().trim()

                if (textoEscrito.equals(correoUsuarioActual, ignoreCase = true)) {
                    // El correo coincide: Eliminamos la cuenta y mandamos al Login
                    Toast.makeText(requireContext(), "Cuenta eliminada correctamente", Toast.LENGTH_LONG).show()
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Borra el historial
                    startActivity(intent)
                } else {
                    // El correo no coincide
                    Toast.makeText(requireContext(), "El correo no coincide. Operación cancelada.", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("CANCELAR") { dialog, _ ->
                dialog.dismiss()
            }
            .show()

    }
}