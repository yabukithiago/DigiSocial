package com.examples.digisocial.ui.view.visit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.data.repository.VisitRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.util.Date

data class AttendanceState(
    var data: Date = Date(),
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)

class AttendanceRegisterViewModel : ViewModel() {
    var state = mutableStateOf(AttendanceState())
        private set
    private val db = Firebase.firestore

    private val date
        get() = state.value.data

    fun attendanceRegister(id: String, onSuccess: () -> Unit){
        val beneficiaryRef = db.collection("beneficiary").document(id)
        db.runTransaction { transaction ->
            val snapshot = transaction.get(beneficiaryRef)
            val newVisits = snapshot.getLong("numeroVisita")!! + 1
            transaction.update(beneficiaryRef, "numeroVisita", newVisits)
        }.addOnSuccessListener {
            VisitRepository.addVisit(id = id, data = date, onSuccess = onSuccess, onFailure = { } )
        }.addOnFailureListener {
            state.value = state.value.copy(errorMessage = it.message)
        }
    }
}