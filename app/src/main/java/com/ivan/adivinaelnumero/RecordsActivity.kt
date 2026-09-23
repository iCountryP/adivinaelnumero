package com.ivan.adivinaelnumero

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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


        val intentVolver = Intent(this, MainActivity::class.java)
        val buttonVolver = findViewById<Button>(R.id.buttonVolver)
        val records = findViewById<TextView>(R.id.tvRecords)


        // Trae de vuelta el dato pasado por MainActivity bajo el nombre de intentos.
        // El defaultValue es porque en caso de que no traiga ningun valor, le pone ese y no peta.
        val numeroIntentos = intent.getIntExtra("intentos", 0)

        records.setText("Intentos: " + numeroIntentos)
        buttonVolver.setOnClickListener() {
            startActivity(intentVolver)

        }
    }
}