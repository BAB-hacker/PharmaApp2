package com.example.pharmaapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "pharma.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        // USERS
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS user(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                email TEXT UNIQUE,
                password TEXT,
                role TEXT
            )
        """)

        // MEDICAMENT ADMIN / CLIENT
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS medicament(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nom TEXT,
                prix REAL,
                quantite INTEGER,
                dateExpiration TEXT
            )
        """)

        // ADMIN par défaut
        val adminValues = ContentValues().apply {
            put("name", "Admin")
            put("email", "admin@pharma.com")
            put("password", "1234")
            put("role", "admin")
        }
        db.insert("user", null, adminValues)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS user")
        db.execSQL("DROP TABLE IF EXISTS medicament")
        onCreate(db)
    }

    // ===== USERS =====
    fun addUser(name: String, email: String, password: String, role: String = "client"): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", name.trim())
            put("email", email.trim())
            put("password", password.trim())
            put("role", role.trim())
        }
        val result = db.insert("user", null, values)
        db.close()
        return result != -1L
    }

    fun loginUser(email: String, password: String): String? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT role, password FROM user WHERE email = ?",
            arrayOf(email.trim())
        )
        var role: String? = null
        if (cursor.moveToFirst()) {
            val dbPassword = cursor.getString(cursor.getColumnIndexOrThrow("password")).trim()
            role = cursor.getString(cursor.getColumnIndexOrThrow("role")).trim()
            if (dbPassword != password.trim()) role = null
        }
        cursor.close()
        db.close()
        return role
    }

    fun getAllUsers(): ArrayList<User> {
        val list = ArrayList<User>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM user", null)
        if (cursor.moveToFirst()) {
            do {
                list.add(User(
                    id = cursor.getInt(0),
                    name = cursor.getString(1),
                    email = cursor.getString(2),
                    password = cursor.getString(3),
                    role = cursor.getString(4)
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list
    }

    fun deleteUser(id: Int) {
        val db = writableDatabase
        db.delete("user", "id=?", arrayOf(id.toString()))
        db.close()
    }

    // ===== MEDICAMENT =====
    fun addMedicament(m: Medicament): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("nom", m.nom)
            put("prix", m.prix)
            put("quantite", m.quantite)
            put("dateExpiration", m.dateExpiration)
        }
        val result = db.insert("medicament", null, values)
        db.close()
        return result != -1L
    }

    fun getAllMedicaments(): ArrayList<Medicament> {
        val list = ArrayList<Medicament>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM medicament", null)
        if (cursor.moveToFirst()) {
            do {
                list.add(Medicament(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    nom = cursor.getString(cursor.getColumnIndexOrThrow("nom")),
                    prix = cursor.getDouble(cursor.getColumnIndexOrThrow("prix")),
                    quantite = cursor.getInt(cursor.getColumnIndexOrThrow("quantite")),
                    dateExpiration = cursor.getString(cursor.getColumnIndexOrThrow("dateExpiration"))
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list
    }

    fun updateMedicament(m: Medicament): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("nom", m.nom)
            put("prix", m.prix)
            put("quantite", m.quantite)
            put("dateExpiration", m.dateExpiration)
        }
        val result = db.update("medicament", values, "id=?", arrayOf(m.id.toString()))
        db.close()
        return result > 0
    }

    fun deleteMedicament(id: Int): Boolean {
        val db = writableDatabase
        val result = db.delete("medicament", "id=?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }
    fun sellMedicine(id: Int): Boolean {
        val db = writableDatabase
        val cursor = db.rawQuery("SELECT quantite FROM medicament WHERE id = ?", arrayOf(id.toString()))
        if (cursor.moveToFirst()) {
            val qty = cursor.getInt(cursor.getColumnIndexOrThrow("quantite"))
            if (qty > 0) {
                val cv = ContentValues()
                cv.put("quantite", qty - 1)
                db.update("medicament", cv, "id=?", arrayOf(id.toString()))
                cursor.close()
                db.close()
                return true
            }
        }
        cursor.close()
        db.close()
        return false
    }
}