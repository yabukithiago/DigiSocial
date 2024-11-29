package com.examples.digisocial.ui.view.show

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.data.models.Voluntary
import com.examples.digisocial.data.repository.VoluntaryRepository

data class ShowVoluntaryState(
    val listVoluntary: List<Voluntary> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ShowVoluntaryViewModel : ViewModel(){
    var state = mutableStateOf(ShowVoluntaryState())
        private set

    fun loadListVoluntary(){
        VoluntaryRepository.getAll{ listVoluntary ->
            state.value = state.value.copy(
                listVoluntary = listVoluntary
            )
        }
    }
}