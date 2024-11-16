import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

data class LoginState(
    var email: String = "",
    var password: String = "",
    val userRole: String? = null,
    var isLoading: Boolean = false,
    var errorMessage: String? = null
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

    fun login(onLoginSuccess: (String) -> Unit, onLoginFailure: (String) -> Unit) {
        val auth = FirebaseAuth.getInstance()
        state.value = state.value.copy(isLoading = true)

        if (email.isEmpty()) {
            state.value = state.value.copy(isLoading = false)
            state.value = state.value.copy(errorMessage = "Email não pode ser vazio")
            return
        }

        if (password.isEmpty()) {
            state.value = state.value.copy(isLoading = false)
            state.value = state.value.copy(errorMessage = "Senha não pode ser vazia")
            return
        }

        auth.signInWithEmailAndPassword(state.value.email, state.value.password)
            .addOnCompleteListener { task ->
                state.value = state.value.copy(isLoading = false)
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    state.value = state.value.copy(errorMessage = null)

                    fetchUserRole(auth.currentUser?.uid) { role ->
                        if (role != null) {
                            onLoginSuccess(role)
                        } else {
                            state.value = state.value.copy(errorMessage = "Tipo de usuário não encontrado.")
                        }
                    }
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    state.value = state.value.copy(errorMessage = task.exception?.message)
                    state.value.isLoading = false
                    onLoginFailure(task.exception?.message ?: "Erro ao fazer login")
                }
            }
    }

    fun logout(onLogoutSuccess: () -> Unit) {
        FirebaseAuth.getInstance().signOut()
        onLogoutSuccess()
    }

    private fun fetchUserRole(userId: String?, onRoleFetched: (String?) -> Unit) {
        if (userId == null) {
            state.value = state.value.copy(errorMessage = "Erro ao obter UID do usuário")
            onRoleFetched(null)
            return
        }

        val db = FirebaseFirestore.getInstance()
        db.collection("user").document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val role = document.getString("role")
                    state.value = state.value.copy(userRole = role)
                    onRoleFetched(role)
                } else {
                    state.value = state.value.copy(errorMessage = "Documento do usuário não encontrado")
                    onRoleFetched(null)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Erro ao buscar o tipo de usuário", exception)
                state.value = state.value.copy(errorMessage = "Erro ao buscar o tipo de usuário")
                onRoleFetched(null)
            }
    }

    fun sendPasswordResetEmail(email: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val auth = FirebaseAuth.getInstance()

        if(email.isNotEmpty()){
            state.value = state.value.copy(errorMessage = "Por favor, insira um email.")
            return
        }

        state.value = state.value.copy(isLoading = true)

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                state.value = state.value.copy(isLoading = false)
                if (task.isSuccessful) {
                    Log.d(TAG, "Email enviado.")
                    onSuccess()
                } else {
                    onFailure(task.exception?.message ?: "Erro ao enviar email")
                }
            }
    }
}