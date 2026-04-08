package com.example.fidelidadmultimarca

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import kotlin.math.hypot

class PuntosFragment : Fragment() {

    private var estadoPantalla = 1

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

        // Enlazar el final de la animación de la píldora por código para evitar errores de XML
        val layoutPuntosContenedor = view.findViewById<View>(R.id.layoutPuntosContenedor)
        layoutPuntosContenedor?.transitionName = "animacion_pildora"

        // EFECTO ONDA / GOTA (Circular Reveal) AL ENTRAR
        view.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                v.removeOnLayoutChangeListener(this)
                val cx = v.width / 2 // Sale del centro
                val cy = 0 // Sale desde arriba (donde estaba la píldora)
                val radioFinal = hypot(v.width.toDouble(), v.height.toDouble()).toFloat()

                try {
                    val anim = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0f, radioFinal)
                    anim.duration = 600 // Onda rápida de medio segundo
                    anim.start()
                } catch (e: Exception) {
                    // Por si la vista se cierra antes de tiempo, evitar crashes
                }
            }
        })

        val contenedorPaneles = view.findViewById<LinearLayout>(R.id.contenedorPaneles)
        val colRestaurantes = view.findViewById<LinearLayout>(R.id.colRestaurantes)
        val colExperiencias = view.findViewById<LinearLayout>(R.id.colExperiencias)

        val tvTituloRestaurantes = view.findViewById<TextView>(R.id.tvTituloRestaurantes)
        val tvTituloExperiencias = view.findViewById<TextView>(R.id.tvTituloExperiencias)
        val logoRincon = view.findViewById<View>(R.id.logoRincon)
        val logoOneLove = view.findViewById<View>(R.id.logoOneLove)
        val logoRebel = view.findViewById<View>(R.id.logoRebel)
        val logoLavapies = view.findViewById<View>(R.id.logoLavapies)
        val logoRing = view.findViewById<View>(R.id.logoRing)
        val logoToledo = view.findViewById<View>(R.id.logoToledo)

        val vistasAnimadas = listOf(
            layoutPuntosContenedor, tvTituloRestaurantes, tvTituloExperiencias,
            logoRincon, logoOneLove, logoRebel, logoLavapies, logoRing, logoToledo
        )

        for (v in vistasAnimadas) {
            (v?.background as? AnimationDrawable)?.apply {
                setEnterFadeDuration(2000)
                setExitFadeDuration(4000)
            }
        }

        fun sincronizarAnimaciones() {
            for (v in vistasAnimadas) {
                (v?.background as? AnimationDrawable)?.apply {
                    stop()
                    selectDrawable(0)
                    start()
                }
            }
        }

        val bgRincon = view.findViewById<View>(R.id.bgRincon)
        val bgOneLove = view.findViewById<View>(R.id.bgOneLove)
        val bgRebel = view.findViewById<View>(R.id.bgRebel)
        val bgLavapies = view.findViewById<View>(R.id.bgLavapies)
        val bgRing = view.findViewById<View>(R.id.bgRing)
        val bgToledo = view.findViewById<View>(R.id.bgToledo)

        val cardRincon = view.findViewById<MaterialCardView>(R.id.cardRincon)
        val cardOneLove = view.findViewById<MaterialCardView>(R.id.cardOneLove)
        val cardRebel = view.findViewById<MaterialCardView>(R.id.cardRebel)
        val cardLava = view.findViewById<MaterialCardView>(R.id.cardLavapies)
        val cardRing = view.findViewById<MaterialCardView>(R.id.cardRing)
        val cardToledo = view.findViewById<MaterialCardView>(R.id.cardToledo)

        fun actualizarVista(nuevoEstado: Int) {
            TransitionManager.beginDelayedTransition(contenedorPaneles, AutoTransition())
            estadoPantalla = nuevoEstado

            val paramRest = colRestaurantes.layoutParams as LinearLayout.LayoutParams
            val paramExp = colExperiencias.layoutParams as LinearLayout.LayoutParams

            if (estadoPantalla == 1) {
                paramRest.weight = 3f
                paramExp.weight = 1f

                bgRincon.visibility = View.VISIBLE; logoRincon?.visibility = View.GONE
                bgOneLove.visibility = View.VISIBLE; logoOneLove?.visibility = View.GONE
                bgRebel.visibility = View.VISIBLE; logoRebel?.visibility = View.GONE

                bgLavapies.visibility = View.GONE; logoLavapies?.visibility = View.VISIBLE
                bgRing.visibility = View.GONE; logoRing?.visibility = View.VISIBLE
                bgToledo.visibility = View.GONE; logoToledo?.visibility = View.VISIBLE
            } else {
                paramRest.weight = 1f
                paramExp.weight = 3f

                bgRincon.visibility = View.GONE; logoRincon?.visibility = View.VISIBLE
                bgOneLove.visibility = View.GONE; logoOneLove?.visibility = View.VISIBLE
                bgRebel.visibility = View.GONE; logoRebel?.visibility = View.VISIBLE

                bgLavapies.visibility = View.VISIBLE; logoLavapies?.visibility = View.GONE
                bgRing.visibility = View.VISIBLE; logoRing?.visibility = View.GONE
                bgToledo.visibility = View.VISIBLE; logoToledo?.visibility = View.GONE
            }
            colRestaurantes.layoutParams = paramRest
            colExperiencias.layoutParams = paramExp

            sincronizarAnimaciones()
        }

        val clicsRest = mapOf(cardRincon to "El Rincón Guay", cardOneLove to "One Love", cardRebel to "Rebel Flame")
        for ((card, nombre) in clicsRest) {
            card.setOnClickListener {
                it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                if (estadoPantalla == 2) actualizarVista(1) else mostrarPopupQR(nombre)
            }
        }

        val clicsExp = mapOf(cardLava to "Sauna Lavapiés", cardRing to "The Private Ring", cardToledo to "Sauna Toledo")
        for ((card, nombre) in clicsExp) {
            card.setOnClickListener {
                it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                if (estadoPantalla == 1) actualizarVista(2) else mostrarPopupQR(nombre)
            }
        }

        view.findViewById<MaterialCardView>(R.id.btnInstrucciones).setOnClickListener {
            Toast.makeText(requireContext(), "1. Expande una columna\n2. Toca tu local para ver el QR", Toast.LENGTH_LONG).show()
        }

        actualizarVista(1)
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