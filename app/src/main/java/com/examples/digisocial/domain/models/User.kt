package com.examples.digisocial.domain.models

open class User (
    open var id: String,
    open var nome: String,
    open var telefone: String,
    open var email: String,
    open var status: String,
    open var role: String,
    open var privileged: Boolean
) {
    companion object {
        fun fromMap(map: Map<String, Any>): User {
            return User(
                map["id"] as String,
                map["nome"] as String,
                map["telefone"] as String,
                map["email"] as String,
                map["status"] as String,
                map["role"] as String,
                map["privileged"] as Boolean
            )
        }
    }
}