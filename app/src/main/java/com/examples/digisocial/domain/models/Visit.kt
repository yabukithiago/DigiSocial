package com.examples.digisocial.domain.models

import java.util.Date

data class Visit(
    var id: String = "",
    var data: Date? = Date(),
) {
    companion object {
        fun fromMap(map: Map<String, Any>): Visit {
            return Visit(
                map["id"] as String,
                map["data"] as Date
            )
        }
    }
}