package com.example.examencorte2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

        val btnEncuesta = findViewById<Button>(R.id.btnEncuesta)
        val btnEstadisticas = findViewById<Button>(R.id.BtnEstadisticas)
        val btnSalir = findViewById<Button>(R.id.button)

        btnEncuesta.setOnClickListener {
            val intent = Intent(this, Encuestas::class.java)
            startActivity(intent)
        }
        btnEstadisticas.setOnClickListener {
            val intent = Intent(this, ResultadosEncuesta::class.java)
            startActivity(intent)
        }
        btnSalir.setOnClickListener {
            finishAffinity()
        }


    }
}