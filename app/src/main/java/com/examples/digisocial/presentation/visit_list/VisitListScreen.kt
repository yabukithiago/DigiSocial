package com.examples.digisocial.presentation.visit_list

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.examples.digisocial.core.printError
import com.examples.digisocial.domain.models.Response
import com.examples.digisocial.domain.models.Visit
import com.examples.digisocial.presentation.visit_list.components.AddVisitAlertDialog
import com.examples.digisocial.presentation.visit_list.visit.VisitListContent
import com.examples.digisocial.presentation.components.LoadingIndicator
import com.examples.digisocial.presentation.components.bars.TopBar
import com.examples.digisocial.presentation.visit_list.visit.components.EmptyVisitListContent
import com.examples.digisocial.presentation.components.buttons.AddFloatingActionButton
import java.util.Date

@Composable
fun VisitListScreen(navController: NavController, beneficiaryId: String, viewModel: VisitListViewModel = hiltViewModel()) {
    val context = LocalContext.current
    var openAddVisitDialog by remember { mutableStateOf(false) }
    var addingVisit by remember { mutableStateOf(false) }

    LaunchedEffect(beneficiaryId) {
        viewModel.getBeneficiaryById(beneficiaryId)
    }

    Scaffold(
        topBar = {
            TopBar(
                title = "Visitas",
                navController = navController,
            )
        },
        floatingActionButton = {
             AddFloatingActionButton(
                 onClick = {
                     openAddVisitDialog = true
                 }
             )
        }
    ) { innerPadding ->
        when (val visitListResponse = viewModel.listaVisitResponse) {
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> {
                visitListResponse.data?.let { beneficiary ->
                    if (beneficiary.visitas.isEmpty()) {
                        EmptyVisitListContent()
                    } else {
                        VisitListContent(
                            innerPadding = innerPadding,
                            visitList = beneficiary.visitas
                        )
                    }
                }
            }
            is Response.Failure -> printError(visitListResponse.e)
        }
    }

    if (openAddVisitDialog) {
        AddVisitAlertDialog(
            onDismissRequest = { openAddVisitDialog = false },
            onConfirm = {
                addingVisit = true
                viewModel.addVisitToBeneficiary(beneficiaryId, Visit("", Date()))
                openAddVisitDialog = false
            }
        )
    }

    if (addingVisit) {
        when (val addVisitResponse = viewModel.addVisitResponse) {
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> addingVisit = false
            is Response.Failure -> printError(addVisitResponse.e)
        }
    }
}