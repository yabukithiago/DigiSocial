package com.examples.digisocial.ui.view.finance

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.examples.digisocial.ui.components.bars.TopBar

@Composable
fun FinanceDashboardView(navController: NavController) {
    val viewModel: FinanceDashboardViewModel = viewModel()
    val state by viewModel.state

    LaunchedEffect(Unit) {
        viewModel.loadListTransaction()
    }
    TopBar(title = "Dashboard Financeiro", navController = navController)

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Saldo: R$ ${(state.totalIncome - state.totalExpense)}",
            style = MaterialTheme.typography.titleLarge
        )

        Row(modifier = Modifier.padding(top = 16.dp)) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Receitas", color = Color.Green)
                Text("R$ ${state.totalIncome}")
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Despesas", color = Color.Red)
                Text("R$ ${state.totalExpense}")
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        PieChart(income = state.totalIncome, expense = state.totalExpense)

    }

}

@Composable
fun PieChart(income: Double, expense: Double) {
    Canvas(modifier = Modifier.size(200.dp)) {
        val total = income + expense
        val incomeAngle = (income / total * 360).toFloat()
        val expenseAngle = (expense / total * 360).toFloat()

        drawArc(
            color = Color.Green,
            startAngle = 0f,
            sweepAngle = incomeAngle,
            useCenter = true
        )
        drawArc(
            color = Color.Red,
            startAngle = incomeAngle,
            sweepAngle = expenseAngle,
            useCenter = true
        )
    }
}

