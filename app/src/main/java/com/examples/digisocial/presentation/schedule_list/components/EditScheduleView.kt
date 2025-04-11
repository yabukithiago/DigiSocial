package com.examples.digisocial.presentation.schedule_list.components

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
import com.examples.digisocial.domain.models.Schedule
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date


@Composable
fun EditScheduleView(
    id: String,
    onDismiss: () -> Unit,
    onEditSchedule: (Schedule) -> Unit
) {
    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current
    var data by remember { mutableStateOf("") }
    var vagasTotais by remember { mutableLongStateOf(0L) }
    var vagasDisponiveis by remember { mutableLongStateOf(0L) }

    LaunchedEffect(id) {
        db.collection("schedule").document(id).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    data = document.getString("data") ?: ""
                    vagasTotais = document.getLong("vagasTotais") ?: 0
                    vagasDisponiveis = document.getLong("vagasDisponiveis") ?: 0
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
                    text = "Editar Escala",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = vagasTotais.toString(),
                    onValueChange = { input ->
                        val numericValue = input.toLongOrNull()
                        if (numericValue != null) {
                            vagasTotais = numericValue
                        } else {
                            Toast.makeText(
                                context, "Número de vagas deve ser um número",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    label = { Text("Vagas Totais") },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp),
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = vagasDisponiveis.toString(),
                    onValueChange = { input ->
                        val numericValue = input.toLongOrNull()
                        if (numericValue != null) {
                            vagasDisponiveis = numericValue
                        } else {
                            Toast.makeText(
                                context, "Número de vagas deve ser um número",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    label = { Text("Vagas Disponiveis") },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp),
                )

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
                            if ( vagasTotais > 0 && vagasDisponiveis > 0
                            ) {
                                onEditSchedule(
                                    Schedule(
                                        id = id,
                                        data = Date(),
                                        vagasTotais = 0,
                                        vagasDisponiveis = 0,
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
