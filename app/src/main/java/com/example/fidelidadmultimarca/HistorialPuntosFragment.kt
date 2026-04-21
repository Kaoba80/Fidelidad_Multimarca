package com.example.fidelidadmultimarca

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment // <--- Esta es la línea que faltaba

class HistorialPuntosFragment : Fragment(R.layout.fragment_historial_puntos) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el botón de volver (la flechita de arriba a la izquierda)
        val btnVolver = view.findViewById<ImageView>(R.id.btnVolverHistorial)

        btnVolver.setOnClickListener {
            // Esto cierra este fragment y vuelve al anterior (PuntosFragment)
            parentFragmentManager.popBackStack()
        }
    }
}