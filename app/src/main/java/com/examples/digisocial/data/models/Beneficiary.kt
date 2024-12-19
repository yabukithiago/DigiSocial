package com.examples.digisocial.data.models

data class Beneficiary(
    val id: String,
    var nome: String,
    var telefone: String,
    val nacionalidade: String,
    var agregadoFamiliar: String,
    var numeroVisita: Long,
    val ownerId: String
) {
    companion object {
        fun fromMap(map: Map<String, Any?>): Beneficiary {
            return Beneficiary(
                map["id"] as String,
                map["nome"] as String,
                map["telefone"] as String,
                map["nacionalidade"] as String,
                map["agregadoFamiliar"] as String,
                map["numeroVisita"] as Long,
                map["ownerId"] as String,
            )
        }
    }
}