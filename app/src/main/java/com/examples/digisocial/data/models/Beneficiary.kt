package com.examples.digisocial.data.models

data class Beneficiary(
    var nome: String,
    var telefone: String,
    val nacionalidade: String,
    var agregadoFamiliar: String,
    var numeroVisitas: Int,
    val ownerId: String
) {
    companion object {
        fun fromMap(map: Map<String, Any>): Beneficiary {
            return Beneficiary(
                map["nome"] as String,
                map["nacionalidade"] as String,
                map["telefone"] as String,
                map["agregadoFamiliar"] as String,
                map["numeroVisitas"] as Int,
                map["ownerId"] as String
            )

        }
    }
}