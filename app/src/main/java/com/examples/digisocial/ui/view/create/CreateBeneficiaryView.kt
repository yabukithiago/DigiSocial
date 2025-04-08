package com.examples.digisocial.ui.view.create

import android.widget.Toast
import com.examples.digisocial.presentation.components.NacionalidadeDropdownMenu
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.examples.digisocial.R
import com.examples.digisocial.domain.models.Beneficiary
import com.examples.digisocial.presentation.components.bars.TopBar

@Composable
fun CreateBeneficiaryView(
    navController: NavController,
    onCreateBeneficiary: (beneficiary: Beneficiary) -> Unit
) {
    val context = LocalContext.current
    var nome by remember { mutableStateOf("") }
    var telemovel by remember { mutableStateOf("") }
    var referencia by remember { mutableStateOf("") }
    var agregadoFamiliar by remember { mutableLongStateOf(0L) }
    var nacionalidade by remember { mutableStateOf("") }
    var pedidos by remember { mutableStateOf("") }
    
    TopBar(title = "Registar Beneficiários", navController = navController)

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(200.dp),
            painter = painterResource(id = R.drawable.digisocial),
            contentDescription = "User Icon"
        )

        TextField(
            value = nome,
            onValueChange = {
                nome = it
            },
            label = { Text("Nome") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = telemovel,
            onValueChange = { 
                telemovel = it
            },
            label = { Text("Telemovel") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = referencia,
            onValueChange = { 
                referencia = it
            },
            label = { Text("Referência") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = agregadoFamiliar.toString(),
            onValueChange = { input ->
                val numericValue = input.toLongOrNull()
                if (numericValue != null) {
                    agregadoFamiliar = numericValue
                } else {
                    Toast.makeText(context, "Agregado Familiar deve ser um número",
                        Toast.LENGTH_SHORT).show()
                }
            },
            label = { Text("Agregado Familiar") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        NacionalidadeDropdownMenu(
            nacionalidade = nacionalidade,
            onNacionalidadeChange = { 
                nacionalidade = it
            },
            isEditing = false
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = pedidos,
            onValueChange = { 
                pedidos = it
            },
            label = { Text("Pedidos") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (nome.isNotEmpty() && telemovel.isNotEmpty()
                    && nacionalidade.isNotEmpty() && agregadoFamiliar > 0
                    && pedidos.isNotEmpty()
                    && referencia.isNotEmpty()) {
                    onCreateBeneficiary(
                        Beneficiary(
                            id = "",
                            nome = nome,
                            telemovel = telemovel,
                            referencia = referencia,
                            agregadoFamiliar = agregadoFamiliar,
                            nacionalidade = nacionalidade,
                            pedidos = pedidos,
                            numeroVisitas = 0,
                            ownerId = ""
                        )
                    )}
                else {
                    Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF044AA6))
        ) {
            Text("Registar Beneficiário")
        }
    }
}