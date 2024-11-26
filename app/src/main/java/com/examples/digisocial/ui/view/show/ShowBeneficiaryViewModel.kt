package com.examples.digisocial.ui.view.show

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.data.models.Beneficiary
import com.examples.digisocial.data.repository.BeneficiaryRepository

data class ShowBeneficiaryState(
    val listBeneficiary: List<Beneficiary> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ShowBeneficiaryViewModel : ViewModel() {
    var state = mutableStateOf(ShowBeneficiaryState())
        private set

    fun loadListBeneficiary() {
        BeneficiaryRepository.getAll { listBeneficiary ->
            state.value = state.value.copy(
                listBeneficiary = listBeneficiary
            )
        }
    }
}