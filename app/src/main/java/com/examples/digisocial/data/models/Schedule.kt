package com.examples.digisocial.data.models

import com.google.firebase.Timestamp
import java.util.Date

data class Schedule(
    val id: String = "",
    var data: Date = Date(),
    var vagasTotais: Int = 0,
    var vagasDisponiveis: Int = 0
) {
    companion object {
        fun fromMap(map: Map<String, Any?>): Schedule {
            return Schedule(
                map["id"] as String,
                data = (map["data"] as? Timestamp)?.toDate() ?: Date(),
                vagasTotais = (map["vagasTotais"] as? Long)?.toInt() ?: 0,
                vagasDisponiveis = (map["vagasDisponiveis"] as? Long)?.toInt() ?: 0
            )
        }
    }
}