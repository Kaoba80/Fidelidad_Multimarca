package com.example.fidelidadmultimarca

import android.os.Bundle
import android.util.Patterns
import android.view.HapticFeedbackConstants
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class EditarPerfilActivity : AppCompatActivity() {

    // La misma validación segura que tienes en el Registro
    private val passwordRegex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[.@#$%^&+=!_\\-]).{6,}$")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)

        val btnCancelar = findViewById<MaterialButton>(R.id.btnCancelarEditarPerfil)
        val btnGuardar = findViewById<MaterialButton>(R.id.btnGuardarPerfil)

        val etNuevoCorreo = findViewById<TextInputEditText>(R.id.etNuevoCorreo)
        val etPassActual = findViewById<TextInputEditText>(R.id.etPassActual)
        val etNuevaPass = findViewById<TextInputEditText>(R.id.etNuevaPass)

        btnCancelar.setOnClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            Toast.makeText(this, "Cambios cancelados", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnGuardar.setOnClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)

            val nuevoCorreo = etNuevoCorreo.text.toString().trim()
            val passActual = etPassActual.text.toString().trim()
            val nuevaPass = etNuevaPass.text.toString().trim()

            val user = FirebaseAuth.getInstance().currentUser

            // 1. Validaciones previas
            if (user == null || user.email == null) {
                Toast.makeText(this, "Error: No hay usuario activo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (nuevoCorreo.isEmpty() && nuevaPass.isEmpty()) {
                Toast.makeText(this, "No hay cambios para guardar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (passActual.isEmpty()) {
                etPassActual.error = "Debes introducir tu contraseña actual por seguridad"
                etPassActual.requestFocus()
                return@setOnClickListener
            }

            if (nuevoCorreo.isNotEmpty() && (!Patterns.EMAIL_ADDRESS.matcher(nuevoCorreo).matches() || !nuevoCorreo.contains("@"))) {
                etNuevoCorreo.error = "Introduce un correo válido"
                etNuevoCorreo.requestFocus()
                return@setOnClickListener
            }

            if (nuevaPass.isNotEmpty() && !nuevaPass.matches(passwordRegex)) {
                etNuevaPass.error = "Mínimo 6 caracteres, 1 mayúscula, 1 número y 1 símbolo"
                etNuevaPass.requestFocus()
                return@setOnClickListener
            }

            // 2. Re-autenticar al usuario (Obligatorio en Firebase por seguridad)
            val credential = EmailAuthProvider.getCredential(user.email!!, passActual)

            user.reauthenticate(credential).addOnCompleteListener { taskAuth ->
                if (taskAuth.isSuccessful) {

                    // Si la contraseña actual es correcta, aplicamos los cambios
                    var cambiosRealizados = 0
                    var operacionesEsperadas = 0

                    if (nuevoCorreo.isNotEmpty()) operacionesEsperadas++
                    if (nuevaPass.isNotEmpty()) operacionesEsperadas++

                    // Actualizar Correo
                    if (nuevoCorreo.isNotEmpty()) {
                        user.updateEmail(nuevoCorreo).addOnCompleteListener { taskEmail ->
                            if (taskEmail.isSuccessful) {
                                cambiosRealizados++
                                comprobarFin(cambiosRealizados, operacionesEsperadas)
                            } else {
                                Toast.makeText(this, "Error al cambiar correo: ${taskEmail.exception?.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                    // Actualizar Contraseña
                    if (nuevaPass.isNotEmpty()) {
                        user.updatePassword(nuevaPass).addOnCompleteListener { taskPass ->
                            if (taskPass.isSuccessful) {
                                cambiosRealizados++
                                comprobarFin(cambiosRealizados, operacionesEsperadas)
                            } else {
                                Toast.makeText(this, "Error al cambiar contraseña: ${taskPass.exception?.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                } else {
                    etPassActual.error = "Contraseña actual incorrecta"
                    etPassActual.requestFocus()
                }
            }
        }
    }

    // Función auxiliar para cerrar la pantalla solo cuando terminen todas las actualizaciones
    private fun comprobarFin(cambiosRealizados: Int, operacionesEsperadas: Int) {
        if (cambiosRealizados == operacionesEsperadas) {
            Toast.makeText(this, "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}