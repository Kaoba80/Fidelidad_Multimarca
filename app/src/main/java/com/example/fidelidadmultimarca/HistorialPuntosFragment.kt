package com.example.fidelidadmultimarca

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment

class HistorialPuntosFragment : Fragment(R.layout.fragment_historial_puntos) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Botón volver
        val btnVolver = view.findViewById<ImageView>(R.id.btnVolverHistorial)
        btnVolver?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}