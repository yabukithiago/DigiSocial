package com.examples.digisocial.presentation.beneficiary_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examples.digisocial.domain.models.Beneficiary
import com.examples.digisocial.domain.models.Response.Loading
import com.examples.digisocial.domain.models.Visit
import com.examples.digisocial.domain.repository.AddBeneficiaryResponse
import com.examples.digisocial.domain.repository.BeneficiaryListResponse
import com.examples.digisocial.domain.repository.BeneficiaryRepository
import com.examples.digisocial.domain.repository.DeleteBeneficiaryResponse
import com.examples.digisocial.domain.repository.UpdateBeneficiaryResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeneficiaryListViewModel @Inject constructor(private val repo: BeneficiaryRepository): ViewModel() {
    var listaBeneficiaryResponse by mutableStateOf<BeneficiaryListResponse>(Loading)
        private set
    var addBeneficiaryResponse by mutableStateOf<AddBeneficiaryResponse>(Loading)
        private set
    var updateBeneficiaryResponse by mutableStateOf<UpdateBeneficiaryResponse>(Loading)
        private set
    var deleteBeneficiaryResponse by mutableStateOf<DeleteBeneficiaryResponse>(Loading)
        private set
    var addVisitBeneficiaryResponse by mutableStateOf<AddBeneficiaryResponse>(Loading)
        private set

    init {
        getBeneficiary()
    }

    private fun getBeneficiary() = viewModelScope.launch {
        repo.getBeneficiary().collect { response ->
            listaBeneficiaryResponse = response
        }
    }

    fun addBeneficiary(beneficiary: Beneficiary) = viewModelScope.launch{
        addBeneficiaryResponse = repo.addBeneficiary(beneficiary)
    }

    fun updateBeneficiary(beneficiary: Beneficiary) = viewModelScope.launch{
        updateBeneficiaryResponse = repo.updateBeneficiary(beneficiary)
    }

    fun deleteBeneficiary(id: String) = viewModelScope.launch {
        deleteBeneficiaryResponse = repo.deleteBeneficiary(id)
    }

    fun addVisitToBeneficiary(beneficiaryId: String, visit: Visit) = viewModelScope.launch {
        addVisitBeneficiaryResponse = repo.addVisitToBeneficiary(beneficiaryId, visit)
    }
}