package com.examples.digisocial.ui.view.visit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.examples.digisocial.data.models.Visit
import com.examples.digisocial.data.repository.VisitRepository.fetchVisit
import com.examples.digisocial.ui.components.bars.TopBar
import com.examples.digisocial.ui.components.cards.AttendanceCard

@Composable
fun ShowAttendanceView(navController: NavController, beneficiaryId: String) {
    var itemList by remember { mutableStateOf(emptyList<Visit>()) }

    LaunchedEffect(beneficiaryId) {
        fetchVisit(
            beneficiaryId = beneficiaryId,
            onSuccess = { fetchedVisits ->
                itemList = fetchedVisits
            },
            onFailure = {

            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar("Visitas", navController = navController)
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(itemList) { item ->
                item.data?.let { AttendanceCard(it) }
            }
        }
    }
}