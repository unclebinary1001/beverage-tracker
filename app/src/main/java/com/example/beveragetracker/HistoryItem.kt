package com.example.beveragetracker

data class HistoryItem(
    val beverageName: String,
    val beverageIcon: Int,
    val time: String,
    val amount: Int,
    val totalAmount: Int,
    val beverageRecord: BeverageRecord
)