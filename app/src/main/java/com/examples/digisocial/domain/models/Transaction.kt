package com.examples.digisocial.domain.models

data class Transaction (
    var id: String = "",
    val description: String = "",
    val amount: Double = 0.0,
    val type: Type = Type.ENTRADA,
    val date: Long = System.currentTimeMillis()
) {
    enum class Type(val value: String) {
        ENTRADA("ENTRADA"),
        SAIDA("SAIDA")
    }
}