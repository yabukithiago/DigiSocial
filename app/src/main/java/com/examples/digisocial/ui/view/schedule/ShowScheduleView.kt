package com.examples.digisocial.ui.view.schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.examples.digisocial.ui.components.bars.TopBar
import com.examples.digisocial.ui.components.cards.ScheduleCard
import com.examples.digisocial.ui.view.login.LoginViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun ShowScheduleView(navController: NavController, modifier: Modifier = Modifier) {
    val viewModel: ShowScheduleViewModel = viewModel()
    val state by viewModel.state
    val auth = Firebase.auth
    val currentUser = auth.currentUser
    val loginViewModel: LoginViewModel = viewModel()
    var role by remember { mutableStateOf("") }

    if (currentUser != null) {
        loginViewModel.fetchUserRole(currentUser.uid) { role = it }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(title = "Escalas", navController = navController)
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(
                    items = state.listSchedule,
                ) { _, item ->
                    ScheduleCard(
                        navController = navController,
                        id = item.id,
                        data = item.data,
                        vagasDisponiveis = item.vagasDisponiveis,
                        onClick = {
                            navController.navigate("showVoluntaryOnSchedule/${item.id}")
                        }
                    )
                }
            }
        }

        if (role == "admin") {
            FloatingActionButton(
                onClick = { navController.navigate("createSchedule") },
                containerColor = Color(0xFF044AA6),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Criar Escala",
                    tint = Color.White
                )
            }
        }
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.loadListSchedule()
    }
}

