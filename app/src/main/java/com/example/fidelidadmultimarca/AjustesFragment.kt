package com.example.fidelidadmultimarca

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView

class AjustesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ajustes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Enlazar vistas (El botón de eliminar ya no está aquí)
        val tvEditarPerfil = view.findViewById<TextView>(R.id.tvEditarPerfil)
        val cardChatAdmin = view.findViewById<MaterialCardView>(R.id.cardChatAdmin)
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

        // 3. Legales
        tvTerminos.setOnClickListener {
            val intent = Intent(requireContext(), CondicionesActivity::class.java)
            startActivity(intent)
        }

        tvAvisoLegal.setOnClickListener {
            Toast.makeText(requireContext(), "Abriendo Aviso Legal...", Toast.LENGTH_SHORT).show()
        }

        // 4. Abrir la nueva pantalla de Preguntas Frecuentes (FAQ)
        tvFaq.setOnClickListener {
            // Buscamos el ID del contenedor donde está "viviendo" este fragmento actualmente
            val containerId = (view.parent as ViewGroup).id

            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out,
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
                )
                .replace(containerId, FaqFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}