package com.examples.digisocial.domain.models

open class User (
    open var id: String,
    open var nome: String,
    open var telefone: String,
    open var email: String,
    open var status: String,
    open var role: String,
    open var privileged: Boolean
)