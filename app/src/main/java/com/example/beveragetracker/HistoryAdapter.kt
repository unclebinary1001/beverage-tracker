package com.example.beveragetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(
    val historyItems: MutableList<HistoryItem>,
    private val onDeleteClick: (BeverageRecord) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val beverageIcon: ImageView = itemView.findViewById(R.id.beverage_icon)
        val beverageName: TextView = itemView.findViewById(R.id.beverage_name)
        val beverageTime: TextView = itemView.findViewById(R.id.beverage_time)
        val beverageAmount: TextView = itemView.findViewById(R.id.beverage_amount)
        val totalBeverageAmount: TextView = itemView.findViewById(R.id.total_beverage_amount)
        val deleteButton: ImageButton = itemView.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_item, parent, false)
        return HistoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val currentItem = historyItems[position]
        holder.beverageIcon.setImageResource(currentItem.beverageIcon)
        holder.beverageName.text = currentItem.beverageName
        holder.beverageTime.text = currentItem.time
        holder.beverageAmount.text = "+${currentItem.amount} ml"
        holder.totalBeverageAmount.text = "Total: ${currentItem.totalAmount} ml"

        holder.deleteButton.setOnClickListener {
            onDeleteClick(currentItem.beverageRecord)
        }
    }

    override fun getItemCount() = historyItems.size
}