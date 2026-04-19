package com.example.fidelidadmultimarca

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class FaqFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_faq, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar FAQ 1
        val cardFaq1 = view.findViewById<MaterialCardView>(R.id.cardFaq1)
        val layoutRespuesta1 = view.findViewById<LinearLayout>(R.id.layoutRespuesta1)
        cardFaq1.setOnClickListener {
            toggleVisibility(layoutRespuesta1)
        }

        // Configurar FAQ 2
        val cardFaq2 = view.findViewById<MaterialCardView>(R.id.cardFaq2)
        val layoutRespuesta2 = view.findViewById<LinearLayout>(R.id.layoutRespuesta2)
        cardFaq2.setOnClickListener {
            toggleVisibility(layoutRespuesta2)
        }

        // Configurar FAQ Eliminar Cuenta
        val cardFaqEliminar = view.findViewById<MaterialCardView>(R.id.cardFaqEliminar)
        val layoutRespuestaEliminar = view.findViewById<LinearLayout>(R.id.layoutRespuestaEliminar)
        cardFaqEliminar.setOnClickListener {
            toggleVisibility(layoutRespuestaEliminar)
        }

        // Botón real de Eliminar Cuenta
        val btnEliminarCuenta = view.findViewById<MaterialButton>(R.id.btnEliminarCuentaFaq)
        btnEliminarCuenta.setOnClickListener {
            // Aquí irá tu lógica para conectar con Firebase/Backend y borrar al usuario
            Toast.makeText(requireContext(), "Iniciando proceso de borrado...", Toast.LENGTH_SHORT).show()
        }
    }

    // Función auxiliar para abrir/cerrar los desplegables
    private fun toggleVisibility(view: View) {
        if (view.visibility == View.GONE) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
}