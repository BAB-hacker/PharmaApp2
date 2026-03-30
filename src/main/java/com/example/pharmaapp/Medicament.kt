package com.example.pharmaapp

data class Medicament(
    var id: Int = 0,
    var nom: String,
    var prix: Double,
    var quantite: Int,
    var dateExpiration: String
)
