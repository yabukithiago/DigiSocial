package com.examples.digisocial.data.models

data class Transaction (
    val id: String = "",
    val description: String = "",
    val amount: Double = 0.0,
    val type: String = "ENTRY",
    val date: Long = System.currentTimeMillis()
)