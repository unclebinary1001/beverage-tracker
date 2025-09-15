package com.example.beveragetracker

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

data class BeverageRecord(
    val beverageName: String,
    val amount: Int,
    val timestamp: Long
)

class HistoryManager(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("beverage_history", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun addBeverageRecord(beverageName: String, amount: Int) {
        val records = getBeverageRecords().toMutableList()
        records.add(0, BeverageRecord(beverageName, amount, Date().time)) // Add to the beginning
        saveBeverageRecords(records)
    }

    fun getBeverageRecords(): List<BeverageRecord> {
        val json = sharedPreferences.getString("records", null)
        return if (json != null) {
            gson.fromJson(json, object : TypeToken<List<BeverageRecord>>() {}.type)
        } else {
            emptyList()
        }
    }

    fun deleteBeverageRecord(record: BeverageRecord) {
        val records = getBeverageRecords().toMutableList()
        records.remove(record)
        saveBeverageRecords(records)
    }

    private fun saveBeverageRecords(records: List<BeverageRecord>) {
        val json = gson.toJson(records)
        sharedPreferences.edit().putString("records", json).apply()
    }
}