package com.examples.digisocial.ui.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.examples.digisocial.ui.view.login.LoginViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@Composable
fun FileImportDropdownMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onFileSelected: (String) -> Unit
) {
    val context = LocalContext.current
    var selectedFilePath by remember { mutableStateOf<String?>(null) }
    val viewModel: LoginViewModel = viewModel()
    val navController = rememberNavController()

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val inputStream: InputStream? = context.contentResolver.openInputStream(it)
            val file = File(context.cacheDir, "imported_file.xlsx")

            inputStream?.use { input ->
                FileOutputStream(file).use { output ->
                    input.copyTo(output)
                }
            }
            selectedFilePath = file.absolutePath
            onFileSelected(file.absolutePath)
        }
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismiss() }
    ) {
        DropdownMenuItem(
            text = { Text("Importar Excel") },
            onClick = {
                filePickerLauncher.launch("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
            }
        )
        DropdownMenuItem(
            text = { Text("Logout") },
            onClick = {
                viewModel.logout(onLogoutSuccess = {
                    navController.navigate("login")
                })
            }
        )
    }
}