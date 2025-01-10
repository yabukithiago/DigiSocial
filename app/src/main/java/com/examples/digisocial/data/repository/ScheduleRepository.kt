package com.examples.digisocial.data.repository

import com.examples.digisocial.data.models.Schedule
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.util.Date

object ScheduleRepository {
    private val db by lazy { Firebase.firestore }

    fun addSchedule(
        id: String, data: Date, tarefa: String,
        onSuccess: () -> Unit, onFailure: (String) -> Unit
    ) {
        val voluntaryRef = db.collection("user").document(id).collection("schedules").document()

        val schedule = Schedule(
            id = voluntaryRef.id,
            data = data,
            tarefa = tarefa
        )

        voluntaryRef.set(schedule)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure("Erro ao adicionar horário: ${exception.message}")
            }
    }

//    fun fetchSchedule(
//        voluntaryId: String,
//        onSuccess: (List<Schedule>) -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//        db.collection("users")
//            .whereEqualTo("role", "voluntary")
//            .get()
//            .addOnSuccessListener {
//                db.collection("schedules")
//                    .get()
//                    .addOnSuccessListener { querySnapshot ->
//                        val schedules =
//                            querySnapshot.documents.mapNotNull {
//                                it.toObject(
//                                    Schedule::class.java
//                                )
//                            }
//                        onSuccess(schedules)
//                    }
//                    .addOnFailureListener { e ->
//                        onFailure("Erro ao buscar horários: ${e.message}")
//                    }
//            }
//    }
}