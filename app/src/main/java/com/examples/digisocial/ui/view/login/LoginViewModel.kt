import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

data class LoginState(
    var email: String = "",
    var password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
class LoginViewModel : ViewModel() {
    var state = mutableStateOf(LoginState())
        private set

    private val email
        get() = state.value.email
    private val password
        get() = state.value.password

    fun onEmailChange(newValue: String) {
        state.value = state.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        state.value = state.value.copy(password = newValue)
    }

    fun login(onLoginSuccess: () -> Unit) {
        val auth = FirebaseAuth.getInstance()
        state.value = state.value.copy(isLoading = true)

        if (email.isEmpty()) {
            state.value = state.value.copy(errorMessage = "Email não pode ser vazio")
            return
        }

        if (password.isEmpty()) {
            state.value = state.value.copy(errorMessage = "Senha não pode ser vazia")
            return
        }

        auth.signInWithEmailAndPassword(state.value.email, state.value.password)
            .addOnCompleteListener { task ->
                state.value = state.value.copy(isLoading = false)
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
//                    val user = auth.currentUser
                    state.value = state.value.copy(errorMessage = null)
                    onLoginSuccess()
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    state.value = state.value.copy(errorMessage = task.exception.toString())
                }
            }
    }
}