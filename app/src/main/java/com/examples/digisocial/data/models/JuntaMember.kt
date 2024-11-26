package com.examples.digisocial.data.models

data class JuntaMember(
    override val id: String,
    override var nome: String,
    override var telefone: String,
    override var email: String,
    override var status: String,
    override var role: String,
    override var privileged: Boolean,
) : User(id, nome, telefone, email, status, role, privileged = false) {

    companion object {
        fun fromMap(map: Map<String, Any>): JuntaMember {
            return JuntaMember(
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