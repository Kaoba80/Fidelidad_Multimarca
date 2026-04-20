package com.example.fidelidadmultimarca

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class AjustesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ajustes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Enlazar vistas
        val tvEditarPerfil = view.findViewById<TextView>(R.id.tvEditarPerfil)
        val tvTerminos = view.findViewById<TextView>(R.id.tvTerminos)
        val tvAvisoLegal = view.findViewById<TextView>(R.id.tvAvisoLegal)
        val tvFaq = view.findViewById<TextView>(R.id.tvFaq)

        // 1. Editar Perfil
        tvEditarPerfil.setOnClickListener {
            val intent = Intent(requireContext(), EditarPerfilActivity::class.java)
            startActivity(intent)
        }

        // 2. Términos y Condiciones
        tvTerminos.setOnClickListener {
            val intent = Intent(requireContext(), CondicionesActivity::class.java)
            startActivity(intent)
        }

        // 3. Aviso Legal
        tvAvisoLegal.setOnClickListener {
            Toast.makeText(requireContext(), "Abriendo Aviso Legal...", Toast.LENGTH_SHORT).show()
        }

        // 4. Abrir la pantalla de Preguntas Frecuentes (FAQ)
        tvFaq.setOnClickListener {
            // Detectamos el ID del contenedor actual de forma dinámica
            val containerId = (view.parent as View).id

            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out,
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
                )
                .replace(containerId, FaqFragment())
                .addToBackStack(null) // Esto permite volver a Ajustes al dar atrás
                .commit()
        }
    }
}