package com.example.senati.Ejercicios

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.senati.MainActivity
import com.example.senati.R

class Ejercicio02 : AppCompatActivity() {

    private lateinit var etMontoVentas: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ejercicio02)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etMontoVentas = findViewById(R.id.etMontoVentas)
        val btnCalcular: Button = findViewById(R.id.btnCalcular)
        val btnRegresar = findViewById<Button>(R.id.btnRegresar)

        btnCalcular.setOnClickListener {
            calcularComision()
        }

        btnRegresar.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun calcularComision() {
        val montoVentasStr = etMontoVentas.text.toString()
        if (montoVentasStr.isNotEmpty()) {
            val montoVentas = montoVentasStr.toDouble()
            var comision: Double
            var bonificacion = 0.0

            when {
                montoVentas < 10000 -> {
                    comision = montoVentas * 0.05
                }
                montoVentas in 10000.0..50000.0 -> {
                    comision = montoVentas * 0.075
                    bonificacion = 200.0
                }
                else -> {
                    comision = montoVentas * 0.09
                    bonificacion = 300.0
                }
            }

            val total = comision + bonificacion
            mostrarResultadoEnModal(total)
            limpiarCampo()
        } else {
            mostrarResultadoEnModal(null)
        }
    }

    private fun mostrarResultadoEnModal(total: Double?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Resultado del Cálculo")

        if (total != null) {
            builder.setMessage("Comisión total: $total")
        } else {
            builder.setMessage("Ingrese el monto de ventas")
        }

        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun limpiarCampo() {
        etMontoVentas.text.clear()
    }
}
