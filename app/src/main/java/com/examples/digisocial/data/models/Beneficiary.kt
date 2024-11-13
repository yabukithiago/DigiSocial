package com.examples.digisocial.data.models

data class Beneficiary(
    val id: String,
    var nome: String,
    var telefone: String,
    val nacionalidade: String,
    var agregadoFamiliar: String,
    var numeroVisitas: Int,
    var pedidos: List<String>
)