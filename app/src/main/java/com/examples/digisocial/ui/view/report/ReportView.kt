package com.examples.digisocial.ui.view.report

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.examples.digisocial.ui.components.bars.TopBar
import com.examples.digisocial.utils.exportBeneficiariesNationalityReport
import com.examples.digisocial.utils.exportFinancialReport

@Composable
fun ReportView(navController: NavController) {
    val context = LocalContext.current

    TopBar(title = "Relatórios", navController = navController)

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                exportBeneficiariesNationalityReport(
                    context = context,
                    onSuccess = {
                        Toast.makeText(
                            context,
                            "Relatório de Nacionalidade exportado com sucesso!",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    onFailure = {
                        Toast.makeText(
                            context,
                            "Falha ao exportar relatório de Nacionalidade.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }, shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(0.6f)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF044AA6))
        ) {
            Text("Exportar Relatório de Nacionalidade")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                exportFinancialReport(
                    context = context,
                    onSuccess = {
                        Toast.makeText(
                            context,
                            "Relatório Financeiro exportado com sucesso!",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    onFailure = {
                        Toast.makeText(
                            context,
                            "Falha ao exportar relatório Financeiro.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }, shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(0.6f)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF044AA6))
        ) {
            Text("Exportar Relatório Financeiro")
        }
    }
}
