package com.examples.digisocial.data.models

data class JuntaMember(
    override val id: String,
    override var nome: String,
    override var telefone: String,
    override var email: String,
    val role: String = "juntamember",
//        val escala: List<Escala>
) : User(id, nome, telefone, email) {

    companion object {
        fun fromMap(map: Map<String, Any>): JuntaMember {
            return JuntaMember(
                map["id"] as String,
                map["nome"] as String,
                map["telefone"] as String,
                map["email"] as String,
            )
        }
    }
}