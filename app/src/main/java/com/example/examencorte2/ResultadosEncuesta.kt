package com.example.examencorte2

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat



class ResultadosEncuesta : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_resultados_encuesta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
        val bd = admin.writableDatabase
        val ContResultados = findViewById<LinearLayout>(R.id.ContResultados)

        // TextViews donde pondremos la respuesta más frecuente
        val Respuesta1 = findViewById<TextView>(R.id.Respuesta1)
        val Respuesta2 = findViewById<TextView>(R.id.Respuesta2)
        val Respuesta3 = findViewById<TextView>(R.id.Respuesta3)
        val Respuesta4 = findViewById<TextView>(R.id.Respuesta4)
        val Respuesta5 = findViewById<TextView>(R.id.Respuesta5)

        // Función que devuelve la respuesta más seleccionada y su cantidad
        fun agarrarRespuestaFrecuente(bd: SQLiteDatabase, campo: String): Pair<String, Int> {
            val cursor = bd.rawQuery(
                "SELECT $campo, COUNT(*) AS total FROM encuesta GROUP BY $campo ORDER BY total DESC LIMIT 1",
                null
            )
            var respuesta = "N/A"
            var total = 0
            if (cursor.moveToFirst()) {
                respuesta = cursor.getString(0)
                total = cursor.getInt(1)
            }
            cursor.close()
            return Pair(respuesta, total)
        }

        // Asignar los valores a cada TextView
        val (resp1, tot1) = agarrarRespuestaFrecuente(bd, "respuesta1")
        val (resp2, tot2) = agarrarRespuestaFrecuente(bd, "respuesta2")
        val (resp3, tot3) = agarrarRespuestaFrecuente(bd, "respuesta3")
        val (resp4, tot4) = agarrarRespuestaFrecuente(bd, "respuesta4")
        val (resp5, tot5) = agarrarRespuestaFrecuente(bd, "respuesta5")

        Respuesta1.text = "1.- Con que frecuencia acudes al cine?  Respuesta mas seleccionada: $resp1 ($tot1 veces)"
        Respuesta2.text = "2.- Califique la calidad de audio de las salas Respuesta mas seleccionada: $resp2 ($tot2 veces)"
        Respuesta3.text = "3.- Califique la atencion del personal antes, durante y despues de la funcion. Respuesta mas seleccionada: $resp3 ($tot3 veces)"
        Respuesta4.text = "4.- Califique el nivel de higiene en baños y salas. Respuesta mas seleccionada: $resp4 ($tot4 veces)"
        Respuesta5.text = "5.- ¿Que tipo de sala prefiere? Respuesta mas seleccionada: $resp5 ($tot5 veces)"




        // Mostrar todas las encuestas en el LinearLayout
        val fila = bd.rawQuery("SELECT nombre, respuesta1, respuesta2, respuesta3, respuesta4, respuesta5 FROM encuesta", null)
        if (fila.moveToFirst()) {
            do {
                val nombre = fila.getString(0)
                val r1 = fila.getString(1)
                val r2 = fila.getString(2)
                val r3 = fila.getString(3)
                val r4 = fila.getString(4)
                val r5 = fila.getString(5)

                val txtResultados = TextView(this)
                txtResultados.text = "Nombre: $nombre\nPregunta 1: $r1\nPregunta 2: $r2\nPregunta 3: $r3\nPregunta 4: $r4\nPregunta 5: $r5"
                txtResultados.setPadding(10, 10, 10, 10)
                txtResultados.textSize = 16f
                txtResultados.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                ContResultados.addView(txtResultados)

            } while (fila.moveToNext())
        } else {
            Toast.makeText(this, "No hay encuestas guardadas", Toast.LENGTH_SHORT).show()
        }

        fila.close()
        bd.close()
        admin.close()
    }
}
