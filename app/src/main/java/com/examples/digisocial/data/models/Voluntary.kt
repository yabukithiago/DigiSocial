package com.examples.digisocial.data.models

data class Voluntary(
        override val id: String,
        override var nome: String,
        override var telefone: String,
        override var email: String,
        val role: String = "voluntary",
//        val escala: List<Escala>
) : User(id, nome, telefone, email)