package com.examples.digisocial.data.models

data class Voluntary(
        override val id: String,
        override var nome: String,
        override var telefone: String,
        override var email: String,
        val privileged: Boolean = false,
        val role: String = "voluntary",
//        val escala: List<Escala>
) : User(id, nome, telefone, email) {

    companion object {
        fun fromMap(map: Map<String, Any>): Voluntary {
            return Voluntary(
                map["id"] as String,
                map["nome"] as String,
                map["telefone"] as String,
                map["email"] as String,
                map["privileged"] as Boolean,
            )
        }
    }
}