package com.examples.digisocial.ui.view.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.examples.digisocial.R
import com.examples.digisocial.ui.components.bars.TopBar

@Composable
fun BeneficiaryButtonView(navController: NavController) {
    TopBar(title = "Beneficiários", navController = navController)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .border(3.dp, Color.Black, CircleShape)
                    .graphicsLayer(scaleX = 0.8f, scaleY = 0.8f),
                painter = painterResource(id = R.drawable.beneficiary),
                contentDescription = "Beneficiary Icon"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("registerBeneficiary") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text("Registar Beneficiários")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { navController.navigate("readBeneficiary") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text("Listar Beneficiários")
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBeneficiaryButtonView() {
   BeneficiaryButtonView(navController = rememberNavController())
}