package com.example.fidelidadmultimarca

import android.os.Bundle
import android.transition.ChangeBounds
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import kotlin.math.hypot

class PuntosFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Recibe la transformación de la píldora de forma fluida
        sharedElementEnterTransition = ChangeBounds().apply {
            duration = 400
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_puntos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutPuntosContenedor = view.findViewById<View>(R.id.layoutPuntosContenedor)
        layoutPuntosContenedor?.transitionName = "animacion_pildora"

        // EFECTO ONDA / GOTA (Circular Reveal) AL ENTRAR
        view.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                v.removeOnLayoutChangeListener(this)
                val cx = v.width / 2
                val cy = 0
                val radioFinal = hypot(v.width.toDouble(), v.height.toDouble()).toFloat()

                try {
                    val anim = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0f, radioFinal)
                    anim.duration = 600
                    anim.start()
                } catch (e: Exception) {
                    // Por si la vista se cierra antes de tiempo, evitar crashes
                }
            }
        })

        // Botón de instrucciones (opcional o de recordatorio)
        view.findViewById<MaterialCardView>(R.id.btnInstrucciones).setOnClickListener {
            Toast.makeText(requireContext(), "Toca nuestro logo central en la pantalla para abrir su código QR.", Toast.LENGTH_LONG).show()
        }
    }

    private fun mostrarPopupQR(nombreLocal: String) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.layout_qr_popup, null)
        val dialog = android.app.Dialog(requireContext(), android.R.style.Theme_NoTitleBar)
        dialog.setContentView(dialogView)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialogView.findViewById<TextView>(R.id.tvNombreLocalQr).text = nombreLocal
        dialogView.findViewById<MaterialButton>(R.id.btnCerrarQr).setOnClickListener { dialog.dismiss() }

        dialogView.alpha = 0f
        dialogView.animate().alpha(1f).setDuration(200).start()
        dialog.show()
    }
}