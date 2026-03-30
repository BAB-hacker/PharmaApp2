package com.example.pharmaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdminMedAdapter(
    private var medicaments: List<Medicament>,
    private val onEdit: (Medicament) -> Unit,
    private val onDelete: (Medicament) -> Unit
) : RecyclerView.Adapter<AdminMedAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvMedName)
        val tvQty: TextView = view.findViewById(R.id.tvMedQty)
        val tvPrice: TextView = view.findViewById(R.id.tvMedPrice)
        val btnEdit: Button = view.findViewById(R.id.btnEditMed)
        val btnDelete: Button = view.findViewById(R.id.btnDeleteMed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_med, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val med = medicaments[position]
        holder.tvName.text = med.nom
        holder.tvQty.text = "Stock: ${med.quantite}"
        holder.tvPrice.text = "Prix: ${med.prix} CFA"

        holder.btnEdit.setOnClickListener { onEdit(med) }
        holder.btnDelete.setOnClickListener { onDelete(med) }
    }

    override fun getItemCount(): Int = medicaments.size

    fun updateData(newList: List<Medicament>) {
        medicaments = newList
        notifyDataSetChanged()
    }
}