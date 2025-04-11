package com.examples.digisocial.domain.models

data class Voluntary(
    override var id: String = "",
    override var nome: String = "",
    override var telefone: String = "",
    override var email: String = "",
    override var status: String = "",
    override var privileged: Boolean = false,
    override var role: String = ""
) : User(id, nome, telefone, email, status, role, privileged)