package com.example.pharmaapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddMedicineActivity : AppCompatActivity() {

    lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medicine)

        db = DatabaseHelper(this)

        val etNom = findViewById<EditText>(R.id.etNom)
        val etPrix = findViewById<EditText>(R.id.etPrix)
        val etQuantite = findViewById<EditText>(R.id.etQuantite)
        val etDate = findViewById<EditText>(R.id.etDate)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {

            if (etNom.text.isEmpty() || etPrix.text.isEmpty() || etQuantite.text.isEmpty()) {
                Toast.makeText(this, "Remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val medicament = Medicament(
                nom = etNom.text.toString(),
                prix = etPrix.text.toString().toDouble(),
                quantite = etQuantite.text.toString().toInt(),
                dateExpiration = etDate.text.toString()
            )

            val success = db.addMedicament(medicament)

            if (success) {
                Toast.makeText(this, "Médicament ajouté", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Erreur", Toast.LENGTH_SHORT).show()
            }
        }
    }
}