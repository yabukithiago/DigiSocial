package com.examples.digisocial.data.repository

import com.examples.digisocial.domain.models.Response
import com.examples.digisocial.domain.models.Schedule
import com.examples.digisocial.domain.models.Voluntary
import com.examples.digisocial.domain.repository.ScheduleRepository
import com.examples.digisocial.domain.repository.ScheduleResponse
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.Date

class ScheduleRepositoryImpl(private val scheduleRef: CollectionReference) : ScheduleRepository {
    override fun getSchedule() = callbackFlow {
        val listener = scheduleRef.addSnapshotListener { lista, e ->
            val listaScheduleResponse = if (lista != null) {
                val listaSchedule = lista.map { scheduleSnapshot ->
                    scheduleSnapshot.toSchedule()
                }
                Response.Success(listaSchedule)
            } else {
                Response.Failure(e)
            }
            trySend(listaScheduleResponse)
        }
        awaitClose{
            listener.remove()
        }
    }

    override suspend fun getScheduleById(id: String) = try {
        val scheduleSnapshot = scheduleRef.document(id).get().await()
        val schedule = scheduleSnapshot.toSchedule()
        Response.Success(schedule)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun getVoluntaryById(voluntaryId: String): ScheduleResponse {
        return try{
            val scheduleSnapshot = scheduleRef.document(voluntaryId).get().await()
            val schedule = scheduleSnapshot.toSchedule()
            val voluntarySnapshot = scheduleRef.document(voluntaryId).collection("voluntary").get().await()
            val voluntaries = voluntarySnapshot.documents.map { it.toObject(Voluntary::class.java)!! }

            Response.Success(schedule.copy(voluntaries = voluntaries))
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun addSchedule(schedule: Schedule): Response<String> {
        return try{
            val id = scheduleRef.document().id
            val scheduleId = schedule.copy(id = id)

            scheduleRef.document(id).set(scheduleId).await()
            Response.Success(id)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun updateSchedule(schedule: Schedule) = try {
        val void = schedule.id.let { id ->
            scheduleRef.document(id).update(
                mapOf(
                    "data" to schedule.data,
                    "vagasTotais" to schedule.vagasTotais,
                    "vagasDisponiveis" to schedule.vagasDisponiveis
                )
            ).await()
        }
        Response.Success(void)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun deleteSchedule(id: String) = try {
        val void = scheduleRef.document(id).delete().await()
        Response.Success(void)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun addVoluntaryToSchedule(
        scheduleId: String,
        voluntary: Voluntary
    ): Response<String>{
        return try {
            val voluntaryRef = scheduleRef.document(scheduleId).collection("voluntary")
            val voluntaryId = scheduleRef.document().id

            val voluntaryWithId = voluntary.copy(id = voluntaryId)
            voluntaryRef.document(voluntaryId).set(voluntaryWithId).await()
            Response.Success(voluntaryId)
        } catch (e:Exception){
            Response.Failure(e)
        }
    }
}

fun DocumentSnapshot.toSchedule() = Schedule(
    id = id,
    data = getDate("data") ?: Date(),
    vagasTotais = getLong("vagasTotais")?.toInt() ?: 0,
    vagasDisponiveis = getLong("vagasDisponiveis")?.toInt() ?: 0
).apply {
    id = getId()
}