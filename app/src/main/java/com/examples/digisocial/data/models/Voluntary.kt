package com.examples.digisocial.data.models

data class Voluntary(
    override val id: String,
    override var nome: String,
    override var telefone: String,
    override var email: String,
    override var status: String,
    override var privileged: Boolean = false,
    override var role: String,
//        val escala: List<Escala>
) : User(id, nome, telefone, email, status, role, privileged) {

    companion object {
        fun fromMap(map: Map<String, Any>): Voluntary {
            return Voluntary(
                map["id"] as String,
                map["nome"] as String,
                map["telefone"] as String,
                map["email"] as String,
                map["status"] as String,
                map["privileged"] as Boolean,
                map["role"] as String
            )
        }
    }
}