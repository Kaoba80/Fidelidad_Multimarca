package com.example.fidelidadmultimarca

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.card.MaterialCardView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class OfertasFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = Runnable {
        if (viewPager.adapter != null) {
            var nextItem = viewPager.currentItem + 1
            if (nextItem >= viewPager.adapter!!.itemCount) nextItem = 0
            viewPager.setCurrentItem(nextItem, true)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ofertas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- CLIC EN LA PÍLDORA CON ANIMACIÓN SEGURA (SIN CRASH) ---
        val cardPuntosPill = view.findViewById<MaterialCardView>(R.id.cardPuntosPill)
        cardPuntosPill.setOnClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)

            // Forzamos el nombre de la transición por código para evitar fallos del XML
            it.transitionName = "animacion_pildora"

            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true) // ¡ESTA ES LA LÍNEA QUE EVITA QUE LA APP SE CIERRE!
                .addSharedElement(it, "animacion_pildora")
                .replace(R.id.contenedorPrincipal, PuntosFragment())
                .addToBackStack(null)
                .commit()
        }

        configurarCarruselYDots(view)
        configurarDesplegable(view)
        configurarBotonesLocales(view)
        encenderAnimacionesLogos(view)
    }

    private fun configurarCarruselYDots(view: View) {
        viewPager = view.findViewById(R.id.viewPagerOfertas)
        val tabLayoutDots = view.findViewById<TabLayout>(R.id.tabLayoutDots)

        val listaOfertas = listOf(
            Oferta("¡2x1 en Copas!\nTodos los Jueves", "#8E0000"),
            Oferta("-50% Entrada\nAntes de las 2:00", "#FF8F00"),
            Oferta("Botella VIP\nReserva anticipada", "#2A0845")
        )

        viewPager.adapter = OfertasAdapter(listaOfertas)

        TabLayoutMediator(tabLayoutDots, viewPager) { tab, position -> }.attach()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 3000)
            }
        })
    }

    private fun encenderAnimacionesLogos(view: View) {
        val fondos = listOf(
            R.id.bgRinconguay, R.id.bgOneLove, R.id.bgRebelFlame,
            R.id.bgLavapies, R.id.bgRing, R.id.bgToledo
        )
        for (id in fondos) {
            val bgView = view.findViewById<View>(id)
            val anim = bgView.background as? AnimationDrawable
            if (anim != null) {
                anim.setEnterFadeDuration(2000)
                anim.setExitFadeDuration(4000)
                anim.start()
            }
        }
    }

    private fun configurarDesplegable(view: View) {
        val cardNormas = view.findViewById<MaterialCardView>(R.id.cardNormas)
        val iconoDesplegable = view.findViewById<ImageView>(R.id.iconoDesplegable)
        val tvTextoNormas = view.findViewById<TextView>(R.id.tvTextoNormas)

        cardNormas.setOnClickListener {
            TransitionManager.beginDelayedTransition(cardNormas, AutoTransition())
            if (tvTextoNormas.visibility == View.GONE) {
                tvTextoNormas.visibility = View.VISIBLE
                iconoDesplegable.animate().rotation(180f).setDuration(300).start()
            } else {
                tvTextoNormas.visibility = View.GONE
                iconoDesplegable.animate().rotation(0f).setDuration(300).start()
            }
        }
    }

    private fun configurarBotonesLocales(view: View) {
        val botones = listOf(
            R.id.btnRinconguay, R.id.btnOneLove, R.id.btnRebelFlame,
            R.id.btnLavapies, R.id.btnRing, R.id.btnToledo
        )

        for (id in botones) {
            val boton = view.findViewById<ImageView>(id)
            boton.setOnClickListener {
                it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                boton.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100).withEndAction {
                    boton.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
                    mostrarPopupOfertasRetro(boton.id)
                }.start()
            }
        }
    }

    private fun mostrarPopupOfertasRetro(localId: Int) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.layout_ofertas_popup, null)
        val dialog = android.app.Dialog(requireContext(), android.R.style.Theme_NoTitleBar)
        dialog.setContentView(dialogView)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val cardPopup = dialogView.findViewById<MaterialCardView>(R.id.cardPopupOfertas)
        val btnCerrarPopup = dialogView.findViewById<ImageButton>(R.id.btnCerrarPopup)
        val tvTituloLocalPopup = dialogView.findViewById<TextView>(R.id.tvTituloLocalPopup)
        val recyclerOfertas = dialogView.findViewById<RecyclerView>(R.id.recyclerOfertasPopup)

        val listaOfertasCupones: List<OfertaCupon> = when (localId) {
            R.id.btnRinconguay -> {
                tvTituloLocalPopup.text = "El Rincón Guay"
                listOf(OfertaCupon("🌈 Shot Arcoíris - 100 pts"), OfertaCupon("🍹 Cóctel Fantasía - 250 pts"), OfertaCupon("🍻 2x1 en Copas - 300 pts"), OfertaCupon("👕 Camiseta Exclusiva - 600 pts"))
            }
            R.id.btnOneLove -> {
                tvTituloLocalPopup.text = "One Love Madrid"
                listOf(OfertaCupon("💛 Chupito Jäger - 150 pts"), OfertaCupon("🎟 Pulsera Fast-Pass - 400 pts"), OfertaCupon("🍾 Botella VIP - 1000 pts"), OfertaCupon("👑 Reservado VIP - 2000 pts"))
            }
            R.id.btnRebelFlame -> {
                tvTituloLocalPopup.text = "Rebel Flame"
                listOf(OfertaCupon("🍺 Jarra de Cerveza - 150 pts"), OfertaCupon("🌮 Nachos Rebeldes - 200 pts"), OfertaCupon("🔥 Cóctel de Fuego - 300 pts"), OfertaCupon("💨 Shisha Premium - 400 pts"))
            }
            R.id.btnLavapies -> {
                tvTituloLocalPopup.text = "Sauna Lavapiés"
                listOf(OfertaCupon("🥤 Bebida Energética - 100 pts"), OfertaCupon("🔐 Taquilla Gratis - 150 pts"), OfertaCupon("💆 Masaje 15 min - 500 pts"), OfertaCupon("🎟 Entrada 2x1 - 800 pts"))
            }
            R.id.btnRing -> {
                tvTituloLocalPopup.text = "The Private Ring"
                listOf(OfertaCupon("🧥 Guardarropa - 50 pts"), OfertaCupon("🍸 Copa Gratis - 300 pts"), OfertaCupon("🥊 Pase Rápido Finde - 400 pts"), OfertaCupon("🖤 Reservado VIP - 1500 pts"))
            }
            R.id.btnToledo -> {
                tvTituloLocalPopup.text = "Sauna Toledo"
                listOf(OfertaCupon("🧖 Toalla Extra - 50 pts"), OfertaCupon("💦 Bebida Relax - 100 pts"), OfertaCupon("🍊 Zumo Natural - 120 pts"), OfertaCupon("🎫 Pase Mensual - 3000 pts"))
            }
            else -> {
                tvTituloLocalPopup.text = "Ofertas"
                emptyList()
            }
        }

        recyclerOfertas.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerOfertas.adapter = OfertasCuponAdapter(listaOfertasCupones)

        btnCerrarPopup.setOnClickListener {
            val animOut = ObjectAnimator.ofFloat(cardPopup, "alpha", 1f, 0f).setDuration(200)
            animOut.start()
            animOut.addListener(object : android.animation.AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: android.animation.Animator) {
                    dialog.dismiss()
                }
            })
        }

        dialog.show()

        val lineX = ObjectAnimator.ofFloat(cardPopup, "scaleX", 0f, 1f).setDuration(150)
        val lineY = ObjectAnimator.ofFloat(cardPopup, "scaleY", 0f, 0.05f).setDuration(150)
        val expandY = ObjectAnimator.ofFloat(cardPopup, "scaleY", 0.05f, 1f).setDuration(300)
        val fadeIn = ObjectAnimator.ofFloat(cardPopup, "alpha", 0f, 1f).setDuration(300)
        fadeIn.interpolator = AccelerateInterpolator()

        val set = AnimatorSet()
        set.play(lineX).with(lineY)
        set.play(expandY).with(fadeIn).after(lineX)
        set.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable)
    }

    data class Oferta(val texto: String, val colorHex: String)

    inner class OfertasAdapter(private val ofertas: List<Oferta>) : RecyclerView.Adapter<OfertasAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvTitulo: TextView = view.findViewById(R.id.tvTituloOferta)
            val fondo: LinearLayout = view.findViewById(R.id.fondoOferta)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_carrusel, parent, false)
            return ViewHolder(view)
        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val oferta = ofertas[position]
            holder.tvTitulo.text = oferta.texto
            holder.fondo.setBackgroundColor(Color.parseColor(oferta.colorHex))
        }
        override fun getItemCount() = ofertas.size
    }

    data class OfertaCupon(val texto: String)

    inner class OfertasCuponAdapter(private val ofertas: List<OfertaCupon>) : RecyclerView.Adapter<OfertasCuponAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvTituloCupon: TextView = view.findViewById(R.id.tvTituloOfertaCupon)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_oferta_cupon, parent, false)
            return ViewHolder(view)
        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tvTituloCupon.text = ofertas[position].texto
        }
        override fun getItemCount() = ofertas.size
    }
}