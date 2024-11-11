package com.examples.digisocial.data.models

data class Manager(
    override val id: String,
    override var nome: String,
    override var telefone: String,
    override var email: String,
    val role: String = "manager",
    val privilegios: Boolean
) : User(id, nome, telefone, email)