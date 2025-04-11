package com.examples.digisocial.domain.models

data class Beneficiary(
    var id: String,
    var nome: String,
    var telemovel: String,
    var referencia: String,
    var agregadoFamiliar: Long,
    val nacionalidade: String,
    var pedidos: String,
    var numeroVisitas: Long,
    val ownerId: String,
    var visitas: List<Visit> = listOf(),
)