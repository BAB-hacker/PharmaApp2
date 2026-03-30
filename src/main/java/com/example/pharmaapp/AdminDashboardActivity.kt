package com.example.pharmaapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AdminDashboardActivity : AppCompatActivity() {

    lateinit var db: DatabaseHelper
    lateinit var rvMedicaments: RecyclerView
    lateinit var rvUsers: RecyclerView
    lateinit var medAdapter: AdminMedAdapter
    lateinit var userAdapter: AdminUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        db = DatabaseHelper(this)

        // RecyclerView pour les médicaments
        rvMedicaments = findViewById(R.id.rvMedicaments)
        rvMedicaments.layoutManager = LinearLayoutManager(this)
        medAdapter = AdminMedAdapter(
            db.getAllMedicaments(),
            onEdit = { medicament -> showEditMedDialog(medicament) },
            onDelete = { medicament ->
                db.deleteMedicament(medicament.id)
                refreshMedicaments()
            }
        )
        rvMedicaments.adapter = medAdapter

        // RecyclerView pour les utilisateurs
        rvUsers = findViewById(R.id.rvUsers)
        rvUsers.layoutManager = LinearLayoutManager(this)
        userAdapter = AdminUserAdapter(
            db.getAllUsers(),
            onDelete = { user ->
                db.deleteUser(user.id)
                refreshUsers()
            }
        )
        rvUsers.adapter = userAdapter

        // Ajouter un médicament
        val btnAddMed = findViewById<Button>(R.id.btnAddMed)
        btnAddMed.setOnClickListener { showAddMedDialog() }
    }

    private fun refreshMedicaments() {
        medAdapter.updateData(db.getAllMedicaments())
    }

    private fun refreshUsers() {
        userAdapter.updateData(db.getAllUsers())
    }

    private fun showAddMedDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_medicine, null)
        val etName = dialogView.findViewById<EditText>(R.id.etMedName)
        val etPrice = dialogView.findViewById<EditText>(R.id.etMedPrice)
        val etQty = dialogView.findViewById<EditText>(R.id.etMedQty)
        val etDate = dialogView.findViewById<EditText>(R.id.etMedDate)

        AlertDialog.Builder(this)
            .setTitle("Ajouter Médicament")
            .setView(dialogView)
            .setPositiveButton("Ajouter") { _, _ ->
                val med = Medicament(
                    id = 0,
                    nom = etName.text.toString(),
                    prix = etPrice.text.toString().toDoubleOrNull() ?: 0.0,
                    quantite = etQty.text.toString().toIntOrNull() ?: 0,
                    dateExpiration = etDate.text.toString()
                )
                val success = db.addMedicament(med)
                if (success) Toast.makeText(this, "Médicament ajouté", Toast.LENGTH_SHORT).show()
                refreshMedicaments()
            }
            .setNegativeButton("Annuler", null)
            .show()
    }

    private fun showEditMedDialog(med: Medicament) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_medicine, null)
        val etName = dialogView.findViewById<EditText>(R.id.etMedName)
        val etPrice = dialogView.findViewById<EditText>(R.id.etMedPrice)
        val etQty = dialogView.findViewById<EditText>(R.id.etMedQty)
        val etDate = dialogView.findViewById<EditText>(R.id.etMedDate)

        etName.setText(med.nom)
        etPrice.setText(med.prix.toString())
        etQty.setText(med.quantite.toString())
        etDate.setText(med.dateExpiration)

        AlertDialog.Builder(this)
            .setTitle("Modifier Médicament")
            .setView(dialogView)
            .setPositiveButton("Modifier") { _, _ ->
                med.nom = etName.text.toString()
                med.prix = etPrice.text.toString().toDoubleOrNull() ?: 0.0
                med.quantite = etQty.text.toString().toIntOrNull() ?: 0
                med.dateExpiration = etDate.text.toString()
                db.updateMedicament(med)
                Toast.makeText(this, "Médicament modifié", Toast.LENGTH_SHORT).show()
                refreshMedicaments()
            }
            .setNegativeButton("Annuler", null)
            .show()
    }
}