package com.examples.digisocial.domain.models

import java.util.Date

data class Schedule(
    var id: String = "",
    var data: Date = Date(),
    var vagasTotais: Int = 0,
    var vagasDisponiveis: Int = 0,
    var voluntaries : List<Voluntary> = listOf(),
)