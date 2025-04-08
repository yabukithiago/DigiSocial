package com.examples.digisocial.presentation.beneficiary_list

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.examples.digisocial.R
import com.examples.digisocial.core.printError
import com.examples.digisocial.core.showToastMessage
import com.examples.digisocial.domain.models.Response
import com.examples.digisocial.domain.models.Visit
import com.examples.digisocial.presentation.Screen
import com.examples.digisocial.presentation.beneficiary_list.components.BeneficiaryListContent
import com.examples.digisocial.presentation.beneficiary_list.components.EmptyBeneficiaryListContent
import com.examples.digisocial.presentation.components.buttons.AddFloatingActionButton
import com.examples.digisocial.presentation.components.LoadingIndicator
import com.examples.digisocial.presentation.components.bars.TopBar
import com.examples.digisocial.ui.view.create.CreateBeneficiaryView
import java.util.Date

@Composable
fun BeneficiaryListScreen(navController: NavController, viewModel: BeneficiaryListViewModel = hiltViewModel()) {
    val context = LocalContext.current
    var openAddBeneficiary by remember { mutableStateOf(false) }
    var addingBeneficiary by remember { mutableStateOf(false) }
    var editingBeneficiary by remember { mutableStateOf(false) }
    var deletingBeneficiary by remember { mutableStateOf(false) }
    var visitingBeneficiary by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(
                title = "Beneficiaries",
                navController = navController,
            )
        },
        floatingActionButton = {
            AddFloatingActionButton {
                openAddBeneficiary = true
            }
        }
    ) { innerPadding ->
        when (val beneficiaryListResponse = viewModel.listaBeneficiaryResponse) {
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> beneficiaryListResponse.data?.let { beneficiaryList ->
                if (beneficiaryList.isEmpty()) {
                    EmptyBeneficiaryListContent()
                } else {
                    BeneficiaryListContent(
                        innerPadding = innerPadding,
                        beneficiaryList = beneficiaryList,
                        onBeneficiaryClick = { beneficiary ->
                            navController.navigate(
                                Screen.BeneficiaryDetailsScreen.createRoute(
                                    beneficiary.id
                                )
                            )
                        },
                        onVisitRegistration = { beneficiary ->
                            viewModel.addVisitToBeneficiary(beneficiary.id, Visit(
                                id = "",
                                data = Date()
                            ))
                            visitingBeneficiary = true

                        },
                        onEditBeneficiary = { beneficiary ->
                            viewModel.updateBeneficiary(beneficiary)
                            editingBeneficiary = true
                        },
                        onDeleteBeneficiary = { id ->
                            viewModel.deleteBeneficiary(id)
                            deletingBeneficiary = true
                        }
                    )
                }
            }

            is Response.Failure -> printError(beneficiaryListResponse.e)
        }
    }

    if (openAddBeneficiary) {
        CreateBeneficiaryView(
            navController = navController,
            onCreateBeneficiary = { beneficiary ->
                viewModel.addBeneficiary(beneficiary)
                addingBeneficiary = true
                showToastMessage(context, R.string.beneficiary_added)
            },
        )
    }

    if (addingBeneficiary) {
        when (val addBeneficiaryResponse = viewModel.addBeneficiaryResponse) {
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> {
                showToastMessage(context, R.string.beneficiary_added)
                addingBeneficiary = false
            }

            is Response.Failure -> {
                printError(addBeneficiaryResponse.e)
                addingBeneficiary = false
            }
        }
    }

    if (editingBeneficiary) {
        when (val updateBeneficiaryResponse = viewModel.updateBeneficiaryResponse) {
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> {
                showToastMessage(context, R.string.beneficiary_updated)
                editingBeneficiary = false
            }

            is Response.Failure -> {
                printError(updateBeneficiaryResponse.e)
                editingBeneficiary = false
            }
        }
    }
    if (deletingBeneficiary) {
        when (val deleteBeneficiaryResponse = viewModel.deleteBeneficiaryResponse) {
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> {
                showToastMessage(context, R.string.beneficiary_deleted)
                deletingBeneficiary = false
            }

            is Response.Failure -> {
                printError(deleteBeneficiaryResponse.e)
                deletingBeneficiary = false
            }
        }
    }

    if(visitingBeneficiary){
        when (val addVisitBeneficiaryResponse = viewModel.addVisitBeneficiaryResponse){
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> {
                showToastMessage(context, R.string.visit_added)
                visitingBeneficiary = false
            }
            is Response.Failure -> {
                printError(addVisitBeneficiaryResponse.e)
                visitingBeneficiary = false
            }
        }
    }
}