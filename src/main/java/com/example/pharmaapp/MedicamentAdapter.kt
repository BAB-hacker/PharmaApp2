package com.example.pharmaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MedicamentAdapter(private val list: List<Medicament>) :
    RecyclerView.Adapter<MedicamentAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNom: TextView = itemView.findViewById(R.id.tvNom)
        val tvPrix: TextView = itemView.findViewById(R.id.tvPrix)
        val tvQuantite: TextView = itemView.findViewById(R.id.tvQuantite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medicament, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val medicament = list[position]

        holder.tvNom.text = medicament.nom
        holder.tvPrix.text = "Prix: ${medicament.prix} FCFA"
        holder.tvQuantite.text = "Quantité: ${medicament.quantite}"

        // ⚠️ Alerte rupture
        if (medicament.quantite <= 5) {
            holder.tvQuantite.setTextColor(android.graphics.Color.RED)
        }
    }
}