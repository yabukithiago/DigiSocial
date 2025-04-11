package com.examples.digisocial.domain.models

data class JuntaMember(
    override var id: String,
    override var nome: String,
    override var telefone: String,
    override var email: String,
    override var status: String,
    override var role: String,
    override var privileged: Boolean,
) : User(id, nome, telefone, email, status, role, privileged = false)