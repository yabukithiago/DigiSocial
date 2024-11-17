package com.examples.digisocial.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.examples.digisocial.ui.view.register.RegisterBeneficiaryState
import com.examples.digisocial.utils.getCountryNames
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NacionalidadeDropdownMenu(
    state: RegisterBeneficiaryState,
    onNacionalidadeChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var nacionalidades by remember { mutableStateOf<List<String>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                nacionalidades = getCountryNames().sorted()
                isLoading = false
            } catch (e: Exception) {
                isLoading = false
            }
        }
    }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, enabled = true),
            value = state.nacionalidade,
            onValueChange = { },
            label = { Text("Nacionalidade") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            shape = RoundedCornerShape(12.dp),
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            readOnly = true
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            nacionalidades.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onNacionalidadeChange(selectionOption)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun NacionalidadeDropdownMenuPreview() {
    val state = RegisterBeneficiaryState()
    NacionalidadeDropdownMenu(state, onNacionalidadeChange = {})
}