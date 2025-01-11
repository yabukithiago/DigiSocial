package com.examples.digisocial.ui.view.edit

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.examples.digisocial.R
import com.examples.digisocial.data.models.JuntaMember
import com.examples.digisocial.data.models.User
import com.examples.digisocial.data.models.Voluntary
import com.examples.digisocial.ui.components.bars.TopBar
import com.examples.digisocial.ui.theme.DigiSocialTheme
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun EditUserView(navController: NavController, id: String) {
    val viewModel: EditUserViewModel = viewModel()
    val state by viewModel.state
    val db = FirebaseFirestore.getInstance()
    var isVoluntario by remember { mutableStateOf(false) }
    var isMembroJunta by remember { mutableStateOf(false) }
    var hasPrivilegios by remember { mutableStateOf(false) }
    var user by remember { mutableStateOf<User>(User("", "", "", "", "", "", false))}
    val context = LocalContext.current

    LaunchedEffect(id) {
        db.collection("user").document(id).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    state.nome = document.getString("nome") ?: ""
                    state.email = document.getString("email") ?: ""
                    state.telefone = document.getString("telefone") ?: ""
                    state.nacionalidade = document.getString("nacionalidade") ?: ""
                    state.agregadoFamiliar = document.getString("agregadoFamiliar") ?: ""
                    state.numeroVisitas = (document.getLong("numeroVisitas") ?: 0).toInt()
                    state.status = document.getString("status") ?: ""
                    state.role = document.getString("role") ?: ""
                    state.privileged = (document.getBoolean("privileged") ?: "") as Boolean
                }
            }
            .addOnFailureListener { e ->
                Log.e("EditUser", "Erro ao carregar utilizador: ${e.message}")
            }
    }

    TopBar(title = "Editar Utilizadores", navController = navController)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(150.dp),
            painter = painterResource(id = R.drawable.baseline_edit_24),
            contentDescription = "Edit Icon"
        )

        TextField(
            value = state.nome,
            onValueChange = viewModel::onNomeChange,
            label = { Text("Nome") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.telefone,
            onValueChange = viewModel::onTelefoneChange,
            label = { Text("Telefone") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Voluntário?",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Checkbox(
                checked = isVoluntario,
                onCheckedChange = { checked ->
                    isVoluntario = checked
                    state.role = "voluntary"
                    if (checked) {
                        Voluntary(
                            id = id, nome = state.nome, telefone = state.telefone,
                            email = state.email,
                            role = state.role, status = "ativo", privileged = false
                        )
                    } else {
                        User(id = id, nome = state.nome, telefone = state.telefone,
                            email = state.email, status = "pendente", role = "", privileged = false)
                    }
                }
            )

            Spacer(modifier = Modifier.width(32.dp))

            Text(
                text = "Privilégios?",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Checkbox(
                checked = hasPrivilegios,
                onCheckedChange = { checked ->
                    hasPrivilegios = checked
                    state.role = "voluntary"
                    state.privileged = true
                    if (isVoluntario) {
                        user = Voluntary(id = id, nome = state.nome, telefone = state.telefone,
                            email = state.email,
                            role = state.role, status = "ativo", privileged = state.privileged)
                    }
                },
                enabled = isVoluntario
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Membro da Junta?",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Checkbox(
                checked = isMembroJunta,
                onCheckedChange = { checked ->
                    isMembroJunta = checked
                    state.role = "juntamember"
                    if (isMembroJunta) {
                        user = JuntaMember(id = id, nome = state.nome, telefone = state.telefone,
                            email = state.email,
                            role = state.role, status = "ativo", privileged = false)
                    } else {
                        user = User(id = id, nome = state.nome, telefone = state.telefone,
                            email = state.email, status = "pendente", role = "", privileged = false)
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (state.nome.isNotEmpty() && state.telefone.isNotEmpty()) {
                    viewModel.update(id, onSuccess = {
                        Toast.makeText(context, "Utilizador editado com sucesso", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()})
                } else {
                    state.errorMessage = "Preencha todos os campos."
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            enabled = !state.isLoading
        ) {
            Text(if (state.isLoading) "Carregando..." else "Editar Utilizador")
        }

        if (state.errorMessage?.isNotEmpty() == true) {
            state.errorMessage?.let { Text(text = it, color = MaterialTheme.colorScheme.error) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditUserViewPreview() {
    DigiSocialTheme {
        EditUserView(navController = rememberNavController(), id="123")
    }
}