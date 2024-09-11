package com.example.senati.Ejercicios

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.senati.MainActivity
import com.example.senati.R

class Ejercicio01 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ejercicio01)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etSalarioBasico = findViewById<EditText>(R.id.etSalarioBasico)
        val etNumeroHijos = findViewById<EditText>(R.id.etNumeroHijos)
        val rgCargo = findViewById<RadioGroup>(R.id.rgCargo)
        val btnCalcular = findViewById<Button>(R.id.btnCalcular)
        val tvResultado = findViewById<TextView>(R.id.tvResultado)
        val btnRegresar = findViewById<Button>(R.id.btnRegresar)

        btnCalcular.setOnClickListener {
            val salarioBasico = etSalarioBasico.text.toString().toFloatOrNull() ?: 0f
            val numeroHijos = etNumeroHijos.text.toString().toIntOrNull() ?: 0
            val selectedCargoId = rgCargo.checkedRadioButtonId
            val cargo = when (selectedCargoId) {
                R.id.rbObrero -> "Obrero"
                R.id.rbEmpleado -> "Empleado"
                else -> "No especificado"
            }

            val bonificacion = when (cargo) {
                "Obrero" -> 100f
                "Empleado" -> 120f
                else -> 0f
            }
            val asignacion = numeroHijos * 41f
            val sueldoTotal = salarioBasico + bonificacion + asignacion

            tvResultado.text = """
                SOLUCIÓN:
                Sueldo básico: $salarioBasico
                Nº de hijos: $numeroHijos
                Cargo: $cargo
                Sueldo Total: $sueldoTotal
            """.trimIndent()

            // Limpiar los campos después de mostrar el resultado
            etSalarioBasico.text.clear()
            etNumeroHijos.text.clear()
            rgCargo.clearCheck()
        }

        btnRegresar.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}