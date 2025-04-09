package com.examples.digisocial.presentation.juntamember_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examples.digisocial.domain.models.Response.Loading
import com.examples.digisocial.domain.repository.DeleteJuntaMemberResponse
import com.examples.digisocial.domain.repository.JuntaMemberListResponse
import com.examples.digisocial.domain.repository.JuntaMemberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JuntaMemberListViewModel @Inject constructor(private val repo: JuntaMemberRepository) : ViewModel() {
    var juntaMemberListResponse by mutableStateOf<JuntaMemberListResponse>(Loading)
            private set
    var deleteJuntaMemberResponse by mutableStateOf<DeleteJuntaMemberResponse>(Loading)
        private set

    init {
        getJuntaMemberList()
    }

    private fun getJuntaMemberList() = viewModelScope.launch {
        repo.getJuntaMember().collect { response ->
            juntaMemberListResponse = response
        }
    }

    fun deleteJuntaMember(id: String) = viewModelScope.launch {
        deleteJuntaMemberResponse = repo.deleteJuntaMember(id)
    }
}