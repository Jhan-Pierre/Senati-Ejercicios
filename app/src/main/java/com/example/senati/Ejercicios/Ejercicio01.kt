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
        val btnRegresar = findViewById<Button>(R.id.btnRegresar)

        btnCalcular.setOnClickListener {
            val salarioBasicoStr = etSalarioBasico.text.toString()
            val numeroHijosStr = etNumeroHijos.text.toString()
            val selectedCargoId = rgCargo.checkedRadioButtonId

            if (salarioBasicoStr.isEmpty() || numeroHijosStr.isEmpty() || selectedCargoId == -1) {
                mostrarResultadoEnDialogo("Complete todos los campos")
            } else {
                val salarioBasico = salarioBasicoStr.toFloatOrNull() ?: 0f
                val numeroHijos = numeroHijosStr.toIntOrNull() ?: 0
                val resultado = calcularSalario(salarioBasico, numeroHijos, selectedCargoId)
                mostrarResultadoEnDialogo(resultado)

                limpiarCampos(etSalarioBasico, etNumeroHijos, rgCargo)
            }
        }

        btnRegresar.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun calcularSalario(salarioBasico: Float, numeroHijos: Int, selectedCargoId: Int): String {
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

        return """
            Sueldo básico: $salarioBasico
            Nº de hijos: $numeroHijos
            Cargo: $cargo
            Sueldo Total: $sueldoTotal
        """.trimIndent()
    }

    private fun mostrarResultadoEnDialogo(resultado: String) {
        // Crear y mostrar un AlertDialog con el resultado
        AlertDialog.Builder(this)
            .setTitle("Resultado del Cálculo")
            .setMessage(resultado)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss() // Cerrar el modal
            }
            .create()
            .show()
    }

    private fun limpiarCampos(etSalarioBasico: EditText, etNumeroHijos: EditText, rgCargo: RadioGroup) {
        etSalarioBasico.text.clear()
        etNumeroHijos.text.clear()
        rgCargo.clearCheck()
    }
}


