package com.examples.digisocial.data.models

data class Beneficiary(
    var nome: String,
    var telefone: String,
    val nacionalidade: String,
    var agregadoFamiliar: String,
    var numeroVisitas: Int,
    val ownerId: String
)