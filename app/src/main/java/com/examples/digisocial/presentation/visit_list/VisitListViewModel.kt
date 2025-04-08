package com.examples.digisocial.presentation.visit_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examples.digisocial.domain.models.Response.Loading
import com.examples.digisocial.domain.models.Visit
import com.examples.digisocial.domain.repository.AddVisitResponse
import com.examples.digisocial.domain.repository.BeneficiaryRepository
import com.examples.digisocial.domain.repository.BeneficiaryResponse
import com.examples.digisocial.domain.repository.VisitListResponse
import com.examples.digisocial.domain.repository.VisitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VisitListViewModel @Inject constructor(private val repo: BeneficiaryRepository): ViewModel() {
    var listaVisitResponse by mutableStateOf<BeneficiaryResponse>(Loading)
        private set
    var addVisitResponse by mutableStateOf<AddVisitResponse>(Loading)
        private set

    fun getBeneficiaryById(beneficiaryId: String) = viewModelScope.launch {
        listaVisitResponse = repo.getBeneficiaryById(beneficiaryId)
    }

    fun addVisitToBeneficiary(beneficiaryId: String, visit: Visit) = viewModelScope.launch {
        addVisitResponse = repo.addVisitToBeneficiary(beneficiaryId, visit)
        getBeneficiaryById(beneficiaryId)
    }
}