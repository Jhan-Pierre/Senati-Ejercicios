package com.example.senati.Ejercicios

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.senati.MainActivity
import com.example.senati.R

class Ejercicio05 : AppCompatActivity() {

    private lateinit var spMarca: Spinner
    private lateinit var spTalla: Spinner
    private lateinit var etCantidad: EditText
    private lateinit var btnCalcular: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ejercicio05)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        spMarca = findViewById(R.id.spMarca)
        spTalla = findViewById(R.id.spTalla)
        etCantidad = findViewById(R.id.etCantidad)
        btnCalcular = findViewById(R.id.btnCalcular)
        val btnRegresar = findViewById<Button>(R.id.btnRegresar)

        btnCalcular.setOnClickListener {
            calcularVenta()
        }

        btnRegresar.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun calcularVenta() {
        val marca = spMarca.selectedItem.toString()
        val talla = spTalla.selectedItem.toString().toInt()
        val cantidadStr = etCantidad.text.toString()

        if (cantidadStr.isNotEmpty()) {
            val cantidad = cantidadStr.toInt()
            val precioPorPar = obtenerPrecioPorPar(marca, talla)
            val ventaTotal = precioPorPar * cantidad
            val descuento = calcularDescuento(cantidad, ventaTotal)
            val netoVenta = ventaTotal - descuento
            mostrarResultadoEnModal(marca, talla, cantidad, ventaTotal, descuento, netoVenta)
            limpiarCampos()
        } else {
            mostrarResultadoEnModal(null, null, null, null, null, null)
        }
    }

    private fun obtenerPrecioPorPar(marca: String, talla: Int): Double {
        return when (marca) {
            "Nike" -> when (talla) {
                38 -> 150.0
                40 -> 160.0
                42 -> 160.0
                else -> 0.0
            }
            "Adidas" -> when (talla) {
                38 -> 140.0
                40 -> 150.0
                42 -> 150.0
                else -> 0.0
            }
            "Fila" -> when (talla) {
                38 -> 80.0
                40 -> 85.0
                42 -> 90.0
                else -> 0.0
            }
            else -> 0.0
        }
    }

    private fun calcularDescuento(cantidad: Int, ventaTotal: Double): Double {
        return when (cantidad) {
            in 2..5 -> 0.05 * ventaTotal
            in 6..10 -> 0.08 * ventaTotal
            in 11..20 -> 0.10 * ventaTotal
            else -> if (cantidad > 20) 0.15 * ventaTotal else 0.0
        }
    }

    private fun mostrarResultadoEnModal(marca: String?, talla: Int?, cantidad: Int?, ventaTotal: Double?, descuento: Double?, netoVenta: Double?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Resultado del Cálculo")

        if (ventaTotal != null && descuento != null && netoVenta != null) {
            builder.setMessage("""
            Marca: $marca
            Talla: $talla
            Pares: $cantidad
            SubTotal: $ventaTotal
            Dsct: $descuento
            Neto a pagar: $netoVenta
        """.trimIndent())
        } else {
            builder.setMessage("Por favor, complete todos los campos")
        }

        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun limpiarCampos() {
        spMarca.setSelection(0)
        spTalla.setSelection(0)
        etCantidad.text.clear()
    }
}
