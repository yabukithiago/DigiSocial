package com.examples.digisocial.presentation.voluntary_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examples.digisocial.domain.models.Response.Loading
import com.examples.digisocial.domain.repository.DeleteVoluntaryResponse
import com.examples.digisocial.domain.repository.VoluntaryListResponse
import com.examples.digisocial.domain.repository.VoluntaryRepository
import com.examples.digisocial.domain.repository.VoluntaryResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VoluntaryListViewModel @Inject constructor(private val repo: VoluntaryRepository) : ViewModel() {
    var voluntaryListResponse by mutableStateOf<VoluntaryListResponse>(Loading)
        private set
    var voluntaryResponse by mutableStateOf<VoluntaryResponse>(Loading)
        private set
    var deleteVoluntaryResponse by mutableStateOf<DeleteVoluntaryResponse>(Loading)
        private set

    init {
        getVoluntaryList()
    }

    private fun getVoluntaryList() = viewModelScope.launch {
        repo.getVoluntary().collect { response ->
            voluntaryListResponse = response
        }
    }
    fun getVoluntaryById(voluntaryId: String) = viewModelScope.launch {
        voluntaryResponse = repo.getVoluntaryById(voluntaryId)
    }

    fun deleteVoluntary(id: String) = viewModelScope.launch {
        deleteVoluntaryResponse = repo.deleteVoluntary(id)
    }
}
