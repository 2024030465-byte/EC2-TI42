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
        val EncuestasTotal = findViewById<TextView>(R.id.textView5)



        // Función que devuelve la respuesta más seleccionada y su cantidad
        fun agarrarTodasRespuestas(bd: SQLiteDatabase, campo: String): String {
            val cursor = bd.rawQuery(
                "SELECT $campo, COUNT(*) AS total FROM encuesta GROUP BY $campo ",
                null
            )
            val resultados=StringBuilder()
            if (cursor.moveToFirst()) {
                do {
                    val respuesta = cursor.getString(0)
                    val total = cursor.getInt(1)
                    resultados.append("$respuesta ($total veces)\n")
                } while (cursor.moveToNext())
            } else {
                resultados.append("No hay respuestas")
            }
            cursor.close()
            return resultados.toString()
        }

        Respuesta1.text = "1.- Con qué frecuencia acudes al cine?\n${agarrarTodasRespuestas(bd, "respuesta1")}"
        Respuesta2.text = "2.- Califique la calidad de audio de las salas\n${agarrarTodasRespuestas(bd, "respuesta2")}"
        Respuesta3.text = "3.- Califique la atención del personal antes, durante y después de la función\n${agarrarTodasRespuestas(bd, "respuesta3")}"
        Respuesta4.text = "4.- Califique el nivel de higiene en baños y salas\n${agarrarTodasRespuestas(bd, "respuesta4")}"
        Respuesta5.text = "5.- ¿Qué tipo de sala prefiere?\n${agarrarTodasRespuestas(bd, "respuesta5")}"


        val cursor = bd.rawQuery("SELECT SUM(EncuestasTotales) FROM encuesta", null)
        if(cursor.moveToFirst()) {
            val Encuestastotales = cursor.getInt(0)
            EncuestasTotal.text = "Total de encuestas realizadas: $Encuestastotales"
        }else{
            EncuestasTotal.text = "Total de encuestas realizadas: 0"
        }
        cursor.close()



        bd.close()
        admin.close()
    }
}
