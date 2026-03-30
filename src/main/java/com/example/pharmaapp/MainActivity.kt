package com.example.pharmaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            startActivity(Intent(this, AddMedicineActivity::class.java))
        }

        findViewById<Button>(R.id.btnStock).setOnClickListener {
            startActivity(Intent(this, StockActivity::class.java))
        }
    }
}