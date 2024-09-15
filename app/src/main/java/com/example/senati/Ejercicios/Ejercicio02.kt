package com.example.senati.Ejercicios

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.senati.R

class Ejercicio02 : AppCompatActivity() {

    private lateinit var etMontoVentas: EditText
    private lateinit var tvResultado: TextView

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
        tvResultado = findViewById(R.id.tvResultado)
        val btnCalcular: Button = findViewById(R.id.btnCalcular)

        btnCalcular.setOnClickListener {
            calcularComision()
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
            tvResultado.text = "Comisión total: $total"
        } else {
            tvResultado.text = "Ingrese el monto de ventas"
        }
    }
}