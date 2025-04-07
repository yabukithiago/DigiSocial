package com.examples.digisocial.data.repository

import com.examples.digisocial.domain.models.Response
import com.examples.digisocial.domain.models.Visit
import com.examples.digisocial.domain.repository.VisitRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.util.Date

class VisitRepositoryImpl(private val visitRef: CollectionReference) : VisitRepository {
    override fun getVisit() = callbackFlow {
        val listener = visitRef.addSnapshotListener{ visita, e ->
            val visitaListResponse = if (visita != null) {
                val visitaList = visita.map { visitaSnapshot ->
                    visitaSnapshot.toVisit()
                }
                Response.Success(visitaList)
            } else {
                Response.Failure(e)
            }
            trySend(visitaListResponse)
        }
        awaitClose{
            listener.remove()
        }
    }
}

fun DocumentSnapshot.toVisit() = Visit(
    id = id,
    data = getDate("data") ?: Date(),
).apply{
    id = getId()
}