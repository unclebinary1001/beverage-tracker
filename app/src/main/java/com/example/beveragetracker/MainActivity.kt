package com.example.beveragetracker

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var historyButton: ImageButton
    private lateinit var waterIcon: ImageView
    private lateinit var coffeeIcon: ImageView
    private lateinit var teaIcon: ImageView
    private lateinit var juiceIcon: ImageView
    private lateinit var historyManager: HistoryManager // Change from beverageDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        historyManager = HistoryManager(applicationContext) // Initialize HistoryManager

        historyButton = findViewById(R.id.history_button)
        historyButton.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

        waterIcon = findViewById(R.id.water_icon)
        coffeeIcon = findViewById(R.id.coffee_icon)
        teaIcon = findViewById(R.id.tea_icon)
        juiceIcon = findViewById(R.id.juice_icon)

        waterIcon.setOnClickListener {
            showInputDialog("Water")
        }
        coffeeIcon.setOnClickListener {
            showInputDialog("Coffee")
        }
        teaIcon.setOnClickListener {
            showInputDialog("Tea")
        }
        juiceIcon.setOnClickListener {
            showInputDialog("Juice")
        }
    }

    private fun showInputDialog(beverage: String) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_fluid_input, null)
        val inputField = dialogView.findViewById<EditText>(R.id.inputAmount)
        val title = dialogView.findViewById<TextView>(R.id.beverageName)
        title.text = "Enter amount for $beverage"

        AlertDialog.Builder(this)
            .setTitle("Fluid Intake")
            .setView(dialogView)
            .setPositiveButton("OK") { _, _ ->
                val amount = inputField.text.toString().toIntOrNull()
                if (amount != null) {
                    historyManager.addBeverageRecord(beverage, amount) // Use HistoryManager
                    Toast.makeText(this, "$beverage: $amount ml", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}