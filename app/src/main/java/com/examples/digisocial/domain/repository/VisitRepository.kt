package com.examples.digisocial.domain.repository

import com.examples.digisocial.domain.models.Response
import com.examples.digisocial.domain.models.Visit
import kotlinx.coroutines.flow.Flow

typealias VisitListResponse = Response<List<Visit>>
typealias AddVisitResponse = Response<String>

interface VisitRepository {

    fun getVisit() : Flow<VisitListResponse>
}