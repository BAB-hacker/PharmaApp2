package com.example.pharmaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MedicineAdapter(
    private var medicines: List<Medicament>,
    private val onBuyClick: (Medicament) -> Unit
) : RecyclerView.Adapter<MedicineAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvMedicineName)
        val tvQuantity: TextView = view.findViewById(R.id.tvMedicineQuantity)
        val tvPrice: TextView = view.findViewById(R.id.tvMedicinePrice)
        val btnBuy: Button = view.findViewById(R.id.btnBuy)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medicine, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val med = medicines[position]
        holder.tvName.text = med.nom
        holder.tvQuantity.text = "Stock: ${med.quantite}"
        holder.tvPrice.text = "Prix: ${med.prix} CFA"
        holder.btnBuy.setOnClickListener { onBuyClick(med) }
    }

    override fun getItemCount(): Int = medicines.size

    fun updateData(newList: List<Medicament>) {
        medicines = newList
        notifyDataSetChanged()
    }
}