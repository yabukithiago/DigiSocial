package com.examples.digisocial.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.examples.digisocial.data.models.Schedule
import com.examples.digisocial.data.models.Voluntary
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.util.Date

object ScheduleRepository {
    private val db by lazy { Firebase.firestore }

    fun addSchedule(vagasTotais: Int, data: Date, onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val schedule = Schedule(
            id = "",
            vagasDisponiveis = vagasTotais,
            vagasTotais = vagasTotais,
            data = data,
        )

        db.collection("schedules")
            .add(schedule)
            .addOnSuccessListener {
                db.collection("schedules")
                    .document(it.id)
                    .update("id", it.id)
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener { e ->
                        onFailure("Erro ao adicionar id: ${e.message}")
                    }
            }
            .addOnFailureListener { e ->
                onFailure("Erro ao adicionar horário: ${e.message}")
            }
    }

    fun getAll(onSuccess: (List<Schedule>) -> Unit) {
        db.collection("schedules")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error)
                    return@addSnapshotListener
                }

                val listSchedule = mutableListOf<Schedule>()
                value?.let {
                    for (document in it.documents) {
                        try {
                            document.data?.let { data ->
                                val schedule = Schedule.fromMap(data)
                                listSchedule.add(schedule)
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "Erro ao converter documento: ${document.id}", e)
                        }
                    }
                }
                onSuccess(listSchedule)
            }
    }

    fun deleteSchedule(id: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("schedules").document(id)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun addVoluntaryToSchedule(
        scheduleId: String,
        voluntaryId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val scheduleRef = db.collection("schedules").document(scheduleId)
        val voluntaryRef = scheduleRef.collection("voluntary").document(voluntaryId)

        VoluntaryRepository.getVoluntary(voluntaryId) { voluntary ->
            val voluntaryData = Voluntary(
                id = voluntaryId,
                nome = voluntary.nome,
                telefone = voluntary.telefone,
                email = voluntary.email,
                status = voluntary.status,
                privileged = voluntary.privileged,
                role = voluntary.role
            )

            db.runTransaction { transaction ->
                val snapshot = transaction.get(scheduleRef)

                val vagasDisponiveis = snapshot.getLong("vagasDisponiveis")?.toInt() ?: 0
                if (vagasDisponiveis <= 0) {
                    throw Exception("Não há vagas disponíveis nesta escala.")
                }

                val voluntarySnapshot = transaction.get(voluntaryRef)
                if (voluntarySnapshot.exists()) {
                    throw Exception("Você já está inscrito nesta escala.")
                }

                transaction.update(scheduleRef, "vagasDisponiveis", vagasDisponiveis - 1)
                transaction.set(voluntaryRef, voluntaryData)
            }.addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener { e ->
                onFailure("${e.message}")
            }
        }
    }

    fun removeVoluntaryFromSchedule(
        scheduleId: String,
        voluntaryId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val scheduleRef = db.collection("schedules").document(scheduleId)
        val voluntaryRef = scheduleRef.collection("voluntary").document(voluntaryId)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(scheduleRef)

            val vagasDisponiveis = snapshot.getLong("vagasDisponiveis")?.toInt() ?: 0
            val vagasTotais = snapshot.getLong("vagasTotais")?.toInt() ?: 0

            val voluntarySnapshot = transaction.get(voluntaryRef)
            if (!voluntarySnapshot.exists()) {
                throw Exception("O voluntário não está inscrito nesta escala.")
            }

            transaction.delete(voluntaryRef)

            val updatedVagasDisponiveis = (vagasDisponiveis + 1).coerceAtMost(vagasTotais)
            transaction.update(scheduleRef, "vagasDisponiveis", updatedVagasDisponiveis)
        }.addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener { e ->
            onFailure("${e.message}")
        }
    }

    fun fetchVoluntary(
        scheduleId: String,
        onSuccess: (List<Voluntary>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val voluntaryCollectionRef = db.collection("schedules")
            .document(scheduleId)
            .collection("voluntary")

        voluntaryCollectionRef.get()
            .addOnSuccessListener { querySnapshot ->
                val voluntaryList = querySnapshot.documents.mapNotNull { it.toObject(Voluntary::class.java) }
                onSuccess(voluntaryList)
            }
            .addOnFailureListener { e ->
                onFailure("Erro ao buscar voluntários: ${e.message}")
            }
    }

}