package com.examples.digisocial.presentation.beneficiary_list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.examples.digisocial.domain.models.Beneficiary
import com.examples.digisocial.domain.models.Visit
import com.examples.digisocial.presentation.components.bars.SearchBar
import com.examples.digisocial.presentation.components.cards.BeneficiaryCard
import com.examples.digisocial.ui.view.edit.EditBeneficiaryView

const val NON_EXISTENT_Beneficiary_ID = "NO_ID"

@Composable
fun BeneficiaryListContent(innerPadding: PaddingValues, beneficiaryList: List<Beneficiary>,
                           onBeneficiaryClick: (Beneficiary) -> Unit, onVisitRegistration: (Visit) -> Unit,
                           onEditBeneficiary: (Beneficiary) -> Unit, onDeleteBeneficiary: (String) -> Unit) {
    var editBeneficiaryId by remember { mutableStateOf(NON_EXISTENT_Beneficiary_ID) }
    var searchQuery by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchBar(
                searchQuery = searchQuery,
                onSearchQueryChanged = { searchQuery = it }
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val filteredBeneficiary = if (searchQuery.isNotBlank()) {
                    beneficiaryList.filter { it.nome.contains(searchQuery, ignoreCase = true) }
                } else {
                    beneficiaryList
                }
                items(
                    items = filteredBeneficiary,
                    key = { beneficiary ->
                        beneficiary.id
                    }
                ) { beneficiary ->
                    if (editBeneficiaryId != beneficiary.id) {
                        BeneficiaryCard(
                            beneficiary = beneficiary,
                            onClick = {
                                onBeneficiaryClick(beneficiary)
                            },
                            onVisitRegistration = { visit ->
                                onVisitRegistration(visit)
                            },
                            onEditBeneficiary = {
                                editBeneficiaryId = beneficiary.id
                            },
                            onDeleteBeneficiary = {
                                onDeleteBeneficiary(beneficiary.id)
                                editBeneficiaryId = NON_EXISTENT_Beneficiary_ID
                            }
                        )
                    } else {
                        EditBeneficiaryView(
                            id = beneficiary.id,
                            onDismiss = {
                                editBeneficiaryId = NON_EXISTENT_Beneficiary_ID
                            },
                            onEditBeneficiary = { updatedBeneficiary ->
                                updatedBeneficiary.apply {
                                    if (nome == "") {
                                        // Handle empty name case
                                    } else {
                                        onEditBeneficiary(updatedBeneficiary)
                                    }
                                }
                            })
                    }
                }
            }
        }
    }
}