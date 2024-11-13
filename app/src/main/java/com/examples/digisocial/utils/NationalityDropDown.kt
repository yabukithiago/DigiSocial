import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.examples.digisocial.ui.view.register.RegisterBeneficiaryState
import com.examples.digisocial.ui.view.register.RegisterBeneficiaryViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NacionalidadeDropdownMenu(
    state: RegisterBeneficiaryState,
    onNacionalidadeChange: (String) -> Unit
) {
    val viewModel: RegisterBeneficiaryViewModel = viewModel()
    var expanded by remember { mutableStateOf(false) }
    var nacionalidades by remember { mutableStateOf<List<String>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                Log.d("NacionalidadeDropdown", "Fetching countries...")
                nacionalidades = getCountryNames()
                isLoading = false
                Log.d("NacionalidadeDropdown", "Fetched countries: ${nacionalidades.size}")
            } catch (e: Exception) {
                isLoading = false
                Log.e("NacionalidadeDropdown", "Error fetching countries", e)
            }
        }
    }
}