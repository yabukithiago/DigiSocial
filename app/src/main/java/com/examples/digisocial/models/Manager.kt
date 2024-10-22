package com.examples.digisocial.models

data class Manager(
    val id: Int,
    val name: String,
    var phone: String,
    var email: String,
    val privilegios: Boolean
)