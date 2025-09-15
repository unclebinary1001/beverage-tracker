package com.example.beveragetracker

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryActivity : AppCompatActivity() {

    private lateinit var historyManager: HistoryManager
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var totalAmountTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        historyManager = HistoryManager(applicationContext)

        val historyRecyclerView = findViewById<RecyclerView>(R.id.history_recycler_view)
        historyRecyclerView.layoutManager = LinearLayoutManager(this)

        totalAmountTextView = findViewById(R.id.total_amount)

        val historyItems = mutableListOf<HistoryItem>()
        historyAdapter = HistoryAdapter(historyItems) { beverageRecord ->
            historyManager.deleteBeverageRecord(beverageRecord)
            updateHistoryDisplay()
        }
        historyRecyclerView.adapter = historyAdapter

        updateHistoryDisplay()
    }

    private fun updateHistoryDisplay() {
        val beverageRecords = historyManager.getBeverageRecords()
        val historyItems = mutableListOf<HistoryItem>()
        var totalToday = 0
        val today = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())

        beverageRecords.forEach { record ->
            val entryDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date(record.timestamp))
            if (entryDate == today) {
                totalToday += record.amount
            }

            val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(record.timestamp))
            val iconRes = getBeverageIcon(record.beverageName)
            historyItems.add(HistoryItem(record.beverageName, iconRes, time, record.amount, totalToday, record))
        }
        historyAdapter.historyItems.clear()
        historyAdapter.historyItems.addAll(historyItems)
        historyAdapter.notifyDataSetChanged()
        totalAmountTextView.text = "$totalToday ml"
    }

    private fun getBeverageIcon(beverageName: String): Int {
        return when (beverageName) {
            "Water" -> R.drawable.water
            "Coffee" -> R.drawable.coffee
            "Tea" -> R.drawable.tea
            "Juice" -> R.drawable.juice
            else -> R.drawable.ic_launcher_foreground // Default icon
        }
    }
}