package com.ivan.adivinaelnumero

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Declaración de variables

        // Variable que maneja el num introducido
        val etNumber = findViewById<EditText>(R.id.etNumber)
        // Variable que maneja el botón que envía el núm
        val sendNumber = findViewById<Button>(R.id.sendNumber)

        // Variable que gestiona el TEXTO de intentos
        val intentos = findViewById<TextView>(R.id.tvIntentos)
        intentos.setText("INTENTOS: " + numeroIntentos)

        // Scroll View (scrollView -> Contiene textoHistorial / textoHistorial -> Texto donde sale info)
        val scrollView = findViewById<ScrollView>(R.id.scrollView2) // Variable que maneja el scrollView
        val textoHistorial = findViewById<TextView>(R.id.textoHistorial) // Variable que gestiona el TEXTO del historial

        val intent = Intent(this, RecordsActivity::class.java)
        textoHistorial.setText(historial)
        etNumber.requestFocus() // Recuperamos el focus en el texto



        var trampa = findViewById<TextView>(R.id.trampa)
        // DESCOMENTAR ESTA LÍNEA PARA HACER TESTEOS. TE DA EL NUMERO A ADIVINAR.
        //trampa.setText(numeroRandom.toString())*/
        trampa.setText("") //comenta esta linea y descomenta la de arriba


        fun procesarIntento() {
            // Comprobamos si la casilla es diferente de vacío (por lo tanto, que has metido número).
            // SI NO COMPROBASEMOS ESTO, EL PROGRAMA PETARÍA!!
            if(etNumber.text.toString() != "") {
                numeroIntentos++ // Sumamos +1 al numero de intentos
                intentos.setText("INTENTOS: " + numeroIntentos) // Actualizamos el texto de lols intentos
                val numeroIntroducido = etNumber.text.toString().toInt()
                if (numeroIntroducido == numeroRandom) {
                    textoHistorial.append("Intento " + numeroIntentos +": ¡HAS ACERTADO EL NÚMERO!\n")
                    val etNombre = EditText(this)
                    etNombre.hint = "Introduce tu nombre..."
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder
                        .setMessage("Enhorabuena! Has acertado en " + numeroIntentos + " intentos.")
                        .setTitle("¡HAS GANADO!")
                        .setView(etNombre)
                        .setPositiveButton("Guardar") { dialog, which ->
                            numeroRandom = (Math.random() * 100 + 1).toInt()
                            intentos.setText("INTENTOS: 0")
                            intent.putExtra("intentos", numeroIntentos)
                            intent.putExtra("nombre", etNombre.text.toString())
                            textoHistorial.setText("")
                            numeroIntentos = 0
                            etNumber.setText("")
                            historial = ""
                            startActivity(intent)
                        }.setNegativeButton("Cancelar") { dialog, which ->
                            numeroRandom = (Math.random() * 100 + 1).toInt()
                            intentos.setText("INTENTOS: 0")
                            textoHistorial.setText("")
                            numeroIntentos = 0
                            etNumber.setText("")
                            historial = ""
                        }
                    val dialog: AlertDialog = builder.create()
                    dialog.show()

                } else {
                    if (numeroIntroducido < numeroRandom) {
                        val text = "El número buscado es MÁS GRANDE"
                        val duration = Toast.LENGTH_SHORT
                        val toast = Toast.makeText(this, text, duration)
                        toast.show()
                        textoHistorial.append("Intento " + numeroIntentos +":  " + numeroIntroducido + "  --> El número es MÁS GRANDE.\n")
                    } else {
                        val text = "El número buscado es MÁS PEQUEÑO"
                        val duration = Toast.LENGTH_SHORT
                        val toast = Toast.makeText(this, text, duration)
                        toast.show()
                        textoHistorial.append("Intento " + numeroIntentos +":  " + numeroIntroducido + "  --> El número es MÁS PEQUEÑO.\n")
                    }
                    etNumber.setText("")
                }
                // .text obtiene el contenido/texto del TextView (CharSequence) y .toString() lo convierte a String para poder guardarlo.
                historial = textoHistorial.text.toString();
                scrollView.post {
                    scrollView.fullScroll(View.FOCUS_DOWN)
                }
            }

            // Si no introduces ningún número y dejas la casilla vacía:
            else {
                val text = "No has introducido ningún número."
                val duration = Toast.LENGTH_SHORT // Duración para el toast
                val toast = Toast.makeText(this, text, duration) // Creamos el toast
                toast.show()
            }

        }

        // Añadimos un setOnClickListener al BOTÓN QUE ENVÍA EL NÚMERO. Es decir, si hacen click:
        sendNumber.setOnClickListener {
            procesarIntento()
        }

        etNumber.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                procesarIntento()
                true
            } else {
                false
            }
        }
    } // Fin del onCreate
    // Companion Objects
    companion object {
        var numeroRandom: Int = (Math.random() * 100 + 1).toInt() // 1 to 100
        // INT de numero de intentos. Se actualiza con ++ cada vez q le da al botón
        var numeroIntentos = 0
        var historial = ""
    }
}