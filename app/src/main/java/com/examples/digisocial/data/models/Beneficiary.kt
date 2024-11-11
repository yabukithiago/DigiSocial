package com.examples.digisocial.data.models

data class Beneficiary(
    val id: Int,
    var nome: String,
    var telefone: String,
    val nacionalidade: String,
    var agregadoFamiliar: String,
    var numeroVisitas: Int,
    val role: String = "beneficiary",
    var pedidos: List<String>
)