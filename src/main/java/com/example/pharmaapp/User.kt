package com.example.pharmaapp

data class User(
    var id: Int = 0,
    var name: String,
    var email: String,
    var password: String,
    var role: String
)