
package com.ivan.adivinaelnumero

import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TableRow

class RecordsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_records)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonVolver = findViewById<Button>(R.id.buttonVolver)
        val records = findViewById<TableLayout>(R.id.tableRecords)

        // Recuperamos los datos enviados desde MainActivity mediante el Intent.
        // Si no recibimos "intentos", getIntExtra devuelve 0 por defecto.
        // Si no recibimos "nombre", getStringExtra devuelve null.
        val numeroIntentos = intent.getIntExtra("intentos", 0)
        val nombre = intent.getStringExtra("nombre")

        // savedInstanceState == null comprueba que la Activity no se esté
        // recreando,ej : después de girar el móvil.
        // intent.hasExtra("intentos") comprueba que el Intent contiene
        // el número de intentos enviado desde MainActivity.
        if (savedInstanceState == null && intent.hasExtra("intentos")) {

            // Añadimos un Pair con dos valores: String (nombre) e Int (intentos).
            // Como nombre puede ser null, utilizamos "Anónimo" en ese caso.
            // IOJO!!! ?: comprueba si e null, NO si está vacío ("").
            listaRecords.add(Pair(nombre ?: "Anónimo", numeroIntentos))
        }

        // Ordenamos la lista por el segundo valor del Pair (NUM de intentos),
        // de menor a mayor. it representa cada elemento de la lista.
        listaRecords.sortBy { it.second }

        // Recorremos todos los récords guardados en el ArrayList.
        // Por cada récord, creamos una fila con el nombre y los intentos.
        for (record in listaRecords) {
            val fila = TableRow(this)
            val celdaNombre = TextView(this)
            val celdaIntentos = TextView(this)

            // first es el nombre y second es el número de intentos.
            celdaNombre.text = record.first
            celdaIntentos.text = record.second.toString() //convertido a str

            fila.addView(celdaNombre)
            fila.addView(celdaIntentos)
            records.addView(fila)
        }

        buttonVolver.setOnClickListener() {
            finish() // cierra esta activity y volvemos a la MainActivity
            // que ya estaba abierta.

        }
    }

    companion object {
        // Lista donde guardamos los récords como parejas de nombre e intentos.
        val listaRecords = ArrayList<Pair<String, Int>>()

    }

}
