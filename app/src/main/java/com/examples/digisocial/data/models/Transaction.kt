package com.examples.digisocial.data.models

data class Transaction (
    val id: String = "",
    val description: String = "",
    val amount: Double = 0.0,
    val type: String = "",
    val date: Long = System.currentTimeMillis()
) {
    companion object {
        fun fromMap(map: Map<String, Any>): Transaction {
            return Transaction(
                map["id"] as String,
                map["description"] as String,
                map["amount"] as Double,
                map["type"] as String,
                map["date"] as Long,
            )
        }
    }
}