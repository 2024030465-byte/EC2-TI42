package com.example.examencorte2

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Encuestas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_encuestas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtRespuesta1 = findViewById<RadioGroup>(R.id.RespuestaEnc1)
        val txtRespuesta2 = findViewById<RadioGroup>(R.id.RepuestaEnc2)
        val txtRespuesta3 = findViewById<RadioGroup>(R.id.RespuestaEnc3)
        val txtRespuesta4 = findViewById<RadioGroup>(R.id.RespuestaEnc4)
        val txtRespuesta5 = findViewById<RadioGroup>(R.id.RespuestaEnc5)
        val btnEnviar = findViewById<Button>(R.id.BtnEnviar)
        val btnRegresar = findViewById<Button>(R.id.btnRegresar)
        var Encuestastotales=0

        btnEnviar.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()
            val seleccion1 = txtRespuesta1.checkedRadioButtonId
            val seleccion2 = txtRespuesta2.checkedRadioButtonId
            val seleccion3 = txtRespuesta3.checkedRadioButtonId
            val seleccion4 = txtRespuesta4.checkedRadioButtonId
            val seleccion5 = txtRespuesta5.checkedRadioButtonId

            val respuesta1 = findViewById<RadioButton>(seleccion1)
            val respuesta2 = findViewById<RadioButton>(seleccion2)
            val respuesta3 = findViewById<RadioButton>(seleccion3)
            val respuesta4 = findViewById<RadioButton>(seleccion4)
            val respuesta5 = findViewById<RadioButton>(seleccion5)

            if (respuesta1 == null || respuesta2 == null || respuesta3 == null || respuesta4 == null || respuesta5 == null) {
                Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registro.put("respuesta1", respuesta1.text.toString())
            registro.put("respuesta2", respuesta2.text.toString())
            registro.put("respuesta3", respuesta3.text.toString())
            registro.put("respuesta4", respuesta4.text.toString())
            registro.put("respuesta5", respuesta5.text.toString())
            Encuestastotales++
            registro.put("EncuestasTotales", Encuestastotales)
            bd.insert("encuesta", null, registro)
            bd.close()
            respuesta1.isChecked = false
            respuesta2.isChecked = false
            respuesta3.isChecked = false
            respuesta4.isChecked = false
            respuesta5.isChecked = false
            Toast.makeText(this, "Encuesta guardada", Toast.LENGTH_SHORT).show()



        }

        btnRegresar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}