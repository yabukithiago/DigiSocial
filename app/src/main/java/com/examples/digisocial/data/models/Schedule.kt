package com.examples.digisocial.data.models

import java.util.Date

data class Schedule(
    val id: String = "",
    var data: Date? = Date(),
    val tarefa: String = ""
) {
    companion object {
        fun fromMap(map: Map<String, Any>): Schedule {
            return Schedule(
                map["id"] as String,
                map["data"] as Date,
                map["tarefa"] as String
            )
        }
    }
}