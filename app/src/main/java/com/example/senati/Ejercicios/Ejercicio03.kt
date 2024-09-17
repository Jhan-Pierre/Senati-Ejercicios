package com.example.senati.Ejercicios

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.senati.MainActivity
import com.example.senati.R

class Ejercicio03 : AppCompatActivity() {

    private lateinit var etNombreObrero: EditText
    private lateinit var etHorasTrabajadas: EditText
    private lateinit var rgTipoActividad: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ejercicio03)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etNombreObrero = findViewById(R.id.etNombreObrero)
        etHorasTrabajadas = findViewById(R.id.etHorasTrabajadas)
        rgTipoActividad = findViewById(R.id.rgTipoActividad)
        val btnCalcular: Button = findViewById(R.id.btnCalcular)
        val btnRegresar = findViewById<Button>(R.id.btnRegresar)

        btnCalcular.setOnClickListener {
            calcularJornal()
        }

        btnRegresar.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun calcularJornal() {
        val nombreObrero = etNombreObrero.text.toString()
        val horasTrabajadasStr = etHorasTrabajadas.text.toString()
        val tipoActividadId = rgTipoActividad.checkedRadioButtonId

        if (nombreObrero.isNotEmpty() && horasTrabajadasStr.isNotEmpty() && tipoActividadId != -1) {
            val horasTrabajadas = horasTrabajadasStr.toInt()
            val pagoPorHora = when (tipoActividadId) {
                R.id.rbPintado -> 10.0
                R.id.rbLaqueado -> 12.0
                R.id.rbBarnizado -> 14.0
                else -> 0.0
            }

            val horasNormales = minOf(horasTrabajadas, 40)
            val horasExtras = maxOf(horasTrabajadas - 40, 0)
            val jornalSemanal = horasNormales * pagoPorHora
            val jornalExtra = horasExtras * pagoPorHora * 1.3
            val totalJornal = jornalSemanal + jornalExtra

            mostrarResultadoEnModal(nombreObrero, jornalSemanal, jornalExtra, totalJornal)
            limpiarCampos()
        } else {
            mostrarResultadoEnModal(nombreObrero, null, null, null)
        }
    }

    private fun mostrarResultadoEnModal(nombreObrero: String, jornalSemanal: Double?, jornalExtra: Double?, totalJornal: Double?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Resultado del Cálculo")

        if (jornalSemanal != null && jornalExtra != null && totalJornal != null) {
            builder.setMessage("Nombre: $nombreObrero\nJornal Semanal: $jornalSemanal\nJornal Extra: $jornalExtra\nTotal Jornal: $totalJornal")
        } else {
            builder.setMessage("Por favor, complete todos los campos")
        }

        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun limpiarCampos() {
        etNombreObrero.text.clear()
        etHorasTrabajadas.text.clear()
        rgTipoActividad.clearCheck()
    }
}