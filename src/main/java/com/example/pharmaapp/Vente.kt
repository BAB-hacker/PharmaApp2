package com.example.pharmaapp

data class Vente(
    var id: Int = 0,
    var medicamentId: Int,
    var quantiteVendue: Int,
    var total: Double,
    var date: String
)
