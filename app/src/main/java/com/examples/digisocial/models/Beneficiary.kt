package com.examples.digisocial.models

data class Beneficiary(
    val id: Int,
    var nome: String,
    var telefone: String,
    val nacionalidade: String,
    var agregadoFamiliar: String,
    var numeroVisitas: Int,
    var pedidos: List<String>
)