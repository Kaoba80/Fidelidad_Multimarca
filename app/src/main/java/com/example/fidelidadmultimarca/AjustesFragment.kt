package com.example.fidelidadmultimarca

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

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
        val btnCerrarSesion = view.findViewById<Button>(R.id.btnCerrarSesion)

        val tvNombreUsuario = view.findViewById<TextView>(R.id.tvNombreUsuario)
        val tvEmailUsuario = view.findViewById<TextView>(R.id.tvEmailUsuario)

        // --- CARGAR DATOS DEL USUARIO DESDE FIREBASE ---
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null && user.email != null) {
            val emailCompleto = user.email!!
            // Cogemos todo lo que hay antes del "@" para el nombre
            val nombreExtraido = emailCompleto.substringBefore("@")

            // Ponemos la primera letra en mayúscula para que quede mejor
            tvNombreUsuario.text = nombreExtraido.replaceFirstChar { it.uppercase() }
            tvEmailUsuario.text = emailCompleto
        } else {
            tvNombreUsuario.text = "Usuario Invitado"
            tvEmailUsuario.text = "No hay correo"
        }
        // -----------------------------------------------

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

        // 4. Preguntas Frecuentes (FAQ)
        tvFaq.setOnClickListener {
            val containerId = (view.parent as View).id
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(containerId, FaqFragment())
                .addToBackStack(null)
                .commit()
        }

        // 5. CERRAR SESIÓN
        btnCerrarSesion.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            Toast.makeText(requireContext(), "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show()
        }
    }
}