package com.example.senati

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.senati.Ejercicios.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val btnEjercicio1 = findViewById<Button>(R.id.btnEjercicio1)
        val btnEjercicio2 = findViewById<Button>(R.id.btnEjercicio2)
        val btnEjercicio3 = findViewById<Button>(R.id.btnEjercicio3)
        val btnEjercicio4 = findViewById<Button>(R.id.btnEjercicio4)
        val btnEjercicio5 = findViewById<Button>(R.id.btnEjercicio5)

        btnEjercicio1.setOnClickListener { navigateTo(Ejercicio01::class.java) }
        btnEjercicio2.setOnClickListener { navigateTo(Ejercicio02::class.java) }
        btnEjercicio3.setOnClickListener { navigateTo(Ejercicio03::class.java) }
        btnEjercicio4.setOnClickListener { navigateTo(Ejercicio04::class.java) }
        btnEjercicio5.setOnClickListener { navigateTo(Ejercicio05::class.java) }
    }

    private fun navigateTo(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}