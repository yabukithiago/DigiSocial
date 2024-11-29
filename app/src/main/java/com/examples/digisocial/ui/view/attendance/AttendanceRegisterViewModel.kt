package com.examples.digisocial.ui.view.attendance

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.data.repository.VisitRepository
import java.util.Date

data class AttendanceState(
    var data: Date = Date(),
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)

class AttendanceRegisterViewModel : ViewModel() {
    var state = mutableStateOf(AttendanceState())
        private set

    private val date
        get() = state.value.data

    fun attendanceRegister(id: String, onSuccess: () -> Unit){
        VisitRepository.addVisit(id = id, data = date, onSuccess = onSuccess, onFailure = { } )
    }
}