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

class Ejercicio04 : AppCompatActivity() {

    private lateinit var etBasico: EditText
    private lateinit var rgCargo: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ejercicio04)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etBasico = findViewById(R.id.etBasico)
        rgCargo = findViewById(R.id.rgCargo)
        val btnCalcular: Button = findViewById(R.id.btnCalcular)
        val btnRegresar = findViewById<Button>(R.id.btnRegresar)

        btnCalcular.setOnClickListener {
            calcularRemuneracion()
            limpiarCampo()
        }

        btnRegresar.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun calcularRemuneracion() {
        val basicoStr = etBasico.text.toString()
        val cargoId = rgCargo.checkedRadioButtonId

        if (basicoStr.isNotEmpty() && cargoId != -1) {
            val basico = basicoStr.toDouble()
            val (bonificacion, asignacion, refrigerio) = when (cargoId) {
                R.id.rbObrero -> Triple(100.0, 120.0, 0.0)
                R.id.rbEmpleado -> Triple(120.0, 150.0, 200.0)
                R.id.rbEjecutivo -> Triple(250.0, 500.0, 250.0)
                else -> Triple(0.0, 0.0, 0.0)
            }

            val totalRemuneracion = basico + bonificacion + asignacion + refrigerio
            mostrarResultadoEnModal(basico, bonificacion, asignacion, refrigerio, totalRemuneracion)
        } else {
            mostrarResultadoEnModal(null, null, null, null, null)
        }
    }

    private fun mostrarResultadoEnModal(basico: Double?, bonificacion: Double?, asignacion: Double?, refrigerio: Double?, totalRemuneracion: Double?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Resultado del Cálculo")

        if (totalRemuneracion != null) {
            builder.setMessage("""
                Sueldo básico: $basico
                Bonificación: $bonificacion
                Asignación: $asignacion
                Refrigerio: $refrigerio
                Total: $totalRemuneracion
            """.trimIndent())
        } else {
            builder.setMessage("Por favor, complete todos los campos")
        }

        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun limpiarCampo() {
        etBasico.text.clear()
        rgCargo.clearCheck()
    }
}