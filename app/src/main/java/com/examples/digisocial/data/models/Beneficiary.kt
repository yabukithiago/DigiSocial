package com.examples.digisocial.data.models

data class Beneficiary(
    val id: String,
    var nome: String,
    var telemovel: String,
    var referencia: String,
    var agregadoFamiliar: Long,
    val nacionalidade: String,
    var pedidos: String,
    var numeroVisitas: Long,
    val ownerId: String
) {
    companion object {
        fun fromMap(map: Map<String, Any?>): Beneficiary {
            return Beneficiary(
                map["id"] as String,
                map["nome"] as String,
                map["telemovel"] as String,
                map["referencia"] as String,
                map["agregadoFamiliar"] as Long,
                map["nacionalidade"] as String,
                map["pedidos"] as String,
                (map["numeroVisitas"] as? Long) ?: 0L,
                map["ownerId"] as String,
            )
        }
    }
}