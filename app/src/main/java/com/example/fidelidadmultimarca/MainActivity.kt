package com.example.fidelidadmultimarca

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAlienCentral = findViewById<FloatingActionButton>(R.id.btnAlienCentral)
        val btnNavOfertas = findViewById<LinearLayout>(R.id.btnNavOfertas)
        val btnNavAjustes = findViewById<LinearLayout>(R.id.btnNavAjustes)

        // 1. Efecto "Respiración" futurista
        val animLatido = ObjectAnimator.ofPropertyValuesHolder(
            btnAlienCentral,
            PropertyValuesHolder.ofFloat("scaleX", 1.0f, 1.08f),
            PropertyValuesHolder.ofFloat("scaleY", 1.0f, 1.08f)
        ).apply {
            duration = 1500
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }

        // 2. CLIC EN EL ALIEN: ABRIR LA CÁMARA
        btnAlienCentral.setOnClickListener {
            try {
                val intentCamara = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivity(intentCamara)
            } catch (e: Exception) {
                Toast.makeText(this, "No se pudo abrir la cámara", Toast.LENGTH_SHORT).show()
            }
        }

        // 3. CLIC EN OFERTAS
        btnNavOfertas.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.contenedorPrincipal, OfertasFragment())
                .commit()
        }

        // 4. CLIC EN AJUSTES (Conectado a tu Fragment)
        btnNavAjustes.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.contenedorPrincipal, AjustesFragment())
                .commit()
        }

        // Arrancar en Ofertas por defecto
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.contenedorPrincipal, OfertasFragment())
                .commit()
        }
    }
}