package com.example.pharmaapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ManageUsersActivity : AppCompatActivity() {

    lateinit var db: DatabaseHelper
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: UsersAdapter
    var userList = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_users)

        db = DatabaseHelper(this)

        recyclerView = findViewById(R.id.recyclerViewUsers)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadUsers()
    }

    private fun loadUsers() {
        // Récupérer tous les utilisateurs
        userList = db.getAllUsers()  // On créera cette fonction dans DatabaseHelper
        adapter = UsersAdapter(userList, object : UsersAdapter.OnItemClickListener {
            override fun onDeleteClick(user: User) {
                if (user.role == "admin") {
                    Toast.makeText(this@ManageUsersActivity, "Impossible de supprimer l'admin", Toast.LENGTH_SHORT).show()
                } else {
                    db.deleteUser(user.id)
                    Toast.makeText(this@ManageUsersActivity, "Utilisateur supprimé", Toast.LENGTH_SHORT).show()
                    loadUsers()
                }
            }
        })
        recyclerView.adapter = adapter
    }
}