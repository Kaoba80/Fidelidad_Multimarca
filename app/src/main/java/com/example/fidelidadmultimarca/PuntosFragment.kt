package com.example.fidelidadmultimarca

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.transition.ChangeBounds
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import kotlin.math.hypot

class PuntosFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        // Efecto Circular Reveal al entrar
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
                } catch (e: Exception) { }
            }
        })

        // --- AQUÍ ESTÁ LA MAGIA DEL BOTÓN CENTRAL ---
        val circuloCentralQr = view.findViewById<MaterialCardView>(R.id.circuloCentralQr)
        circuloCentralQr.setOnClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            mostrarPopupQR("Fidelidad Multimarca")
        }

        // Botón de instrucciones
        view.findViewById<MaterialCardView>(R.id.btnInstrucciones).setOnClickListener {
            Toast.makeText(requireContext(), "Toca nuestro logo central para abrir el QR.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mostrarPopupQR(nombreLocal: String) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.layout_qr_popup, null)
        val dialog = android.app.Dialog(requireContext(), android.R.style.Theme_NoTitleBar)
        dialog.setContentView(dialogView)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialogView.findViewById<TextView>(R.id.tvNombreLocalQr).text = nombreLocal

        // --- LÓGICA DEL CÓDIGO QR DINÁMICO ---
        val ivCodigoQR = dialogView.findViewById<ImageView>(R.id.ivCodigoQR)

        // 1. Obtener datos únicos (ID de usuario de Firebase + Hora exacta actual)
        val usuario = FirebaseAuth.getInstance().currentUser
        val uidUsuario = usuario?.uid ?: "UsuarioInvitado"
        val timestamp = System.currentTimeMillis() // Esto cambia cada milisegundo

        // 2. Crear el contenido secreto del QR
        val contenidoQrDinamico = "FIDELIDAD|UID:$uidUsuario|TIME:$timestamp"

        // 3. Generar la imagen y ponerla en pantalla
        val bitmapQR = generarQR(contenidoQrDinamico)
        if (bitmapQR != null) {
            ivCodigoQR.setImageBitmap(bitmapQR)
        } else {
            Toast.makeText(requireContext(), "Error al generar el QR", Toast.LENGTH_SHORT).show()
        }
        // --------------------------------------

        dialogView.findViewById<MaterialButton>(R.id.btnCerrarQr).setOnClickListener { dialog.dismiss() }

        dialogView.alpha = 0f
        dialogView.animate().alpha(1f).setDuration(200).start()
        dialog.show()
    }

    // Función que transforma un texto en una imagen QR (Bitmap) de 500x500 píxeles
    private fun generarQR(contenido: String): Bitmap? {
        return try {
            val bitMatrix = MultiFormatWriter().encode(
                contenido,
                BarcodeFormat.QR_CODE,
                500, // Ancho
                500  // Alto
            )
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

            // Colorear los píxeles (Negro para el código, Blanco para el fondo)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }
            bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }
}