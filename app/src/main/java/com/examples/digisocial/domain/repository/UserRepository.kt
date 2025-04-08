package com.examples.digisocial.domain.repository

import com.examples.digisocial.domain.models.Response
import com.examples.digisocial.domain.models.User
import com.examples.digisocial.domain.models.Voluntary
import kotlinx.coroutines.flow.Flow


typealias UserResponse = Response<User>
typealias UserListResponse = Response<List<User>>
typealias UpdateUserResponse = Response<Void>
typealias DeleteUserResponse = Response<Void>

interface UserRepository {

    fun getVoluntary(): Flow<VoluntaryListResponse>

    fun getUser(): Flow<UserListResponse>

    suspend fun getVoluntaryById(id: String): VoluntaryResponse

    suspend fun getUserById(id: String): UserResponse

    suspend fun updateUser(user: User): UpdateUserResponse

    suspend fun deleteUser(id: String): DeleteUserResponse

    suspend fun deleteVoluntary(id: String): DeleteVoluntaryResponse
}