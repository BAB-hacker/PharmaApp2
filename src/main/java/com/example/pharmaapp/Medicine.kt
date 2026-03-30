package com.example.pharmaapp

data class Medicine(
    val id: Int,
    val name: String,
    var quantity: Int,
    val price: Double
)