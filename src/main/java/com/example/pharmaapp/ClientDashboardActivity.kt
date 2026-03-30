package com.example.pharmaapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ClientDashboardActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MedicineAdapter
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_dashboard)

        db = DatabaseHelper(this)
        recyclerView = findViewById(R.id.recyclerViewMedicines)
        recyclerView.layoutManager = LinearLayoutManager(this)

        btnLogout = findViewById(R.id.btnLogout)
        btnLogout.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        loadMedicines()
    }

    private fun loadMedicines() {
        val medicines = db.getAllMedicaments()
        adapter = MedicineAdapter(medicines) { medicine ->
            val success = db.sellMedicine(medicine.id)
            if (success) {
                Toast.makeText(this, "${medicine.nom} acheté !", Toast.LENGTH_SHORT).show()
                refreshList()
            } else {
                Toast.makeText(this, "Stock insuffisant !", Toast.LENGTH_SHORT).show()
            }
        }
        recyclerView.adapter = adapter
    }

    private fun refreshList() {
        val medicines = db.getAllMedicaments()
        adapter.updateData(medicines)
    }
}