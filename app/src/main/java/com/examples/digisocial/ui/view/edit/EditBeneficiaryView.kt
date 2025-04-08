package com.examples.digisocial.ui.view.edit

import com.examples.digisocial.presentation.components.NacionalidadeDropdownMenu
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.examples.digisocial.domain.models.Beneficiary
import com.google.firebase.firestore.FirebaseFirestore

@Composable 
fun EditBeneficiaryView(
        id: String,
        onDismiss: () -> Unit,
        onEditBeneficiary: (beneficiary: Beneficiary) -> Unit
    ) {
    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current
    var nome by remember { mutableStateOf("") }
    var telemovel by remember { mutableStateOf("") }
    var nacionalidade by remember { mutableStateOf("") }
    var agregadoFamiliar by remember { mutableLongStateOf(0L) }
    var numeroVisitas by remember { mutableLongStateOf(0L) }
    var pedidos by remember { mutableStateOf("") }
    var ownerId by remember { mutableStateOf("") }
    var referencia by remember { mutableStateOf("") }

    LaunchedEffect(id) {
        db.collection("beneficiary").document(id).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    nome = document.getString("nome") ?: ""
                    telemovel = document.getString("telemovel") ?: ""
                    nacionalidade = document.getString("nacionalidade") ?: ""
                    pedidos = document.getString("pedidos") ?: ""
                    agregadoFamiliar = document.getLong("agregadoFamiliar") ?: 0
                    numeroVisitas = document.getLong("numeroVisitas") ?: 0
                    referencia = document.getString("referencia") ?: ""
                    ownerId = document.getString("ownerId") ?: ""
                }
            }
            .addOnFailureListener { e ->
                Log.e("EditBeneficiary", "Erro ao carregar beneficiário: ${e.message}")
            }
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            shadowElevation = 8.dp,
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Editar Beneficiário",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome") },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp),
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = telemovel,
                    onValueChange = { telemovel = it },
                    label = { Text("Telefone") },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp),
                )

                Spacer(modifier = Modifier.height(8.dp))

                NacionalidadeDropdownMenu(
                    nacionalidade = nacionalidade,
                    onNacionalidadeChange = { nacionalidade = it },
                    isEditing = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = agregadoFamiliar.toString(),
                    onValueChange = { input ->
                        val numericValue = input.toLongOrNull()
                        if (numericValue != null) {
                            agregadoFamiliar = numericValue
                        } else {
                            Toast.makeText(
                                context, "Agregado Familiar deve ser um número",
                                Toast.LENGTH_SHORT
                            ).show()
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

                Row {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF044AA6),
                            contentColor = Color.White
                        ),
                    ) {
                        Text("Cancelar")
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = {
                            if (nome.isNotEmpty() && telemovel.isNotEmpty()
                                && nacionalidade.isNotEmpty() && agregadoFamiliar > 0
                            ) {
                                onEditBeneficiary(
                                    Beneficiary(
                                        id = id,
                                        nome = nome,
                                        telemovel = telemovel,
                                        referencia = referencia,
                                        agregadoFamiliar = agregadoFamiliar,
                                        nacionalidade = nacionalidade,
                                        pedidos = pedidos,
                                        numeroVisitas = numeroVisitas,
                                        ownerId = ownerId
                                    )
                                )
                                onDismiss()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Preencha todos os campos",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF044AA6),
                            contentColor = Color.White
                        ),
                    ) {
                        Text("Salvar")
                    }

                }
            }
        }
    }
}