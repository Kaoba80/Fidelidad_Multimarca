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

class FaqFragment : Fragment() {

    // Correo simulado (se movió aquí porque aquí se usa ahora)
    private val correoUsuarioActual = "usuario@fidelidad.com"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_faq, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // FAQ 1
        val cardFaq1 = view.findViewById<MaterialCardView>(R.id.cardFaq1)
        val layoutRespuesta1 = view.findViewById<LinearLayout>(R.id.layoutRespuesta1)
        cardFaq1.setOnClickListener { toggleVisibility(layoutRespuesta1) }

        // FAQ 2
        val cardFaq2 = view.findViewById<MaterialCardView>(R.id.cardFaq2)
        val layoutRespuesta2 = view.findViewById<LinearLayout>(R.id.layoutRespuesta2)
        cardFaq2.setOnClickListener { toggleVisibility(layoutRespuesta2) }

        // FAQ Eliminar Cuenta
        val cardFaqEliminar = view.findViewById<MaterialCardView>(R.id.cardFaqEliminar)
        val layoutRespuestaEliminar = view.findViewById<LinearLayout>(R.id.layoutRespuestaEliminar)
        cardFaqEliminar.setOnClickListener { toggleVisibility(layoutRespuestaEliminar) }

        // Botón Eliminar Cuenta (Llama a TU función)
        val btnEliminarCuentaFaq = view.findViewById<MaterialButton>(R.id.btnEliminarCuentaFaq)
        btnEliminarCuentaFaq.setOnClickListener {
            mostrarDialogoEliminarCuenta()
        }
    }

    private fun toggleVisibility(view: View) {
        if (view.visibility == View.GONE) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    // Tu lógica original, ahora dentro de FAQ
    private fun mostrarDialogoEliminarCuenta() {
        val input = EditText(requireContext())
        input.hint = "Escribe tu correo aquí"
        input.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

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
                    Toast.makeText(requireContext(), "Cuenta eliminada correctamente", Toast.LENGTH_LONG).show()
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "El correo no coincide. Operación cancelada.", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("CANCELAR") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}