package com.example.pharmaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StockActivity : AppCompatActivity() {

    lateinit var db: DatabaseHelper
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)

        db = DatabaseHelper(this)
        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val medicaments = db.getAllMedicaments()
        val adapter = MedicamentAdapter(medicaments)

        recyclerView.adapter = adapter
    }
}