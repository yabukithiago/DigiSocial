package com.examples.digisocial.ui.view.user

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.data.models.User
import com.examples.digisocial.data.repository.UserRepository

data class PendingUserState(
    val listUser: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class PendingUserViewModel : ViewModel() {
    var state = mutableStateOf(PendingUserState())
        private set

    fun loadListUser() {
        UserRepository.getAll { listUser ->
            state.value = state.value.copy(
                listUser = listUser
            )
        }
    }
}

