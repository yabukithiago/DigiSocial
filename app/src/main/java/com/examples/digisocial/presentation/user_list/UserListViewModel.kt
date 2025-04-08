package com.examples.digisocial.presentation.user_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examples.digisocial.domain.models.Response.Loading
import com.examples.digisocial.domain.models.User
import com.examples.digisocial.domain.repository.DeleteUserResponse
import com.examples.digisocial.domain.repository.UpdateUserResponse
import com.examples.digisocial.domain.repository.UserListResponse
import com.examples.digisocial.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(private val repo: UserRepository) : ViewModel() {
    var userListResponse by mutableStateOf<UserListResponse>(Loading)
        private set
    var updateUserResponse by mutableStateOf<UpdateUserResponse>(Loading)
        private set
    var deleteUserResponse by mutableStateOf<DeleteUserResponse>(Loading)
        private set

    init {
        getUserList()
    }

    private fun getUserList() = viewModelScope.launch {
        repo.getUser().collect { response ->
            userListResponse = response
        }
    }

    fun updateUser(user: User) = viewModelScope.launch {
        updateUserResponse = repo.updateUser(user)
    }

    fun deleteUser(id: String) = viewModelScope.launch {
        deleteUserResponse = repo.deleteUser(id)
    }
}
