package com.examples.digisocial.ui.view.delete

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.data.repository.VoluntaryRepository

data class DeleteVoluntaryState(
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)

class DeleteVoluntaryViewModel : ViewModel() {
    var state = mutableStateOf(DeleteVoluntaryState())
        private set

    fun deleteVoluntary(id: String, onSuccess: () -> Unit){
        VoluntaryRepository.deleteVoluntary(id = id, onSuccess = onSuccess, onFailure = { } )
    }
}