package com.examples.digisocial.ui.view.show

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.data.models.JuntaMember
import com.examples.digisocial.data.repository.JuntaMemberRepository

data class ShowJuntaMemberState(
    val listJuntaMember: List<JuntaMember> = emptyList(),
            val isLoading: Boolean = false,
                    val error: String? = null
)

class ShowJuntaMemberViewModel : ViewModel(){
    var state = mutableStateOf(ShowJuntaMemberState())
        private set

    fun loadListJuntaMember(){
        JuntaMemberRepository.getAll{ listJuntaMember ->
            state.value = state.value.copy(
                listJuntaMember = listJuntaMember,
            )
        }
    }
}