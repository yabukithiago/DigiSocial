package com.examples.digisocial.data.repository

import com.examples.digisocial.domain.models.Response
import com.examples.digisocial.domain.models.User
import com.examples.digisocial.domain.models.Voluntary
import com.examples.digisocial.domain.repository.UserRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl(private val userRef: CollectionReference) : UserRepository{
    override fun getVoluntary() = callbackFlow {
        val listener = userRef
            .addSnapshotListener { lista, e ->
                val listaVoluntaryResponse = if (lista != null) {
                    val listaVoluntary = lista.map { voluntarySnapshot ->
                        voluntarySnapshot.toVoluntary()
                    }
                    Response.Success(listaVoluntary)
                } else {
                    Response.Failure(e)
                }
                trySend(listaVoluntaryResponse)
            }
        awaitClose {
            listener.remove()
        }
    }

    override fun getUser() = callbackFlow {
        val listener = userRef
            .whereEqualTo("status", "pendente")
            .addSnapshotListener { lista, e ->
                val listaUserResponse = if (lista != null) {
                    val listaUser = lista.map { userSnapshot ->
                        userSnapshot.toUser()
                    }
                    Response.Success(listaUser)
                } else {
                    Response.Failure(e)
                }
                trySend(listaUserResponse)
            }
        awaitClose {
            listener.remove()
        }
    }

    override suspend fun getVoluntaryById(id: String) = try {
        val userSnapshot = userRef.document(id).get().await()
        val user = if (userSnapshot.getString("role") == "user") {
                userSnapshot.toObject(Voluntary::class.java)
        } else null

        Response.Success(user)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun getUserById(id: String) = try {
        val userSnapshot = userRef.document(id).get().await()
        val user = userSnapshot.toObject(User::class.java)

        Response.Success(user)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun updateUser(user: User) = try {
        val void = user.id.let { id ->
            userRef.document(id).update(
                mapOf(
                    "nome" to user.nome,
                    "telefone" to user.telefone,
                    "email" to user.email,
                    "status" to "ativo",
                    "privileged" to user.privileged,
                    "role" to user.role,
                )
            ).await()
        }
        Response.Success(void)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun deleteUser(id: String) = try {
        val void = userRef.document(id).delete().await()
        Response.Success(void)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun deleteVoluntary(id: String) = try {
        val void = userRef.document(id).update("status", "inativo").await()
        Response.Success(void)
    } catch (e: Exception) {
        Response.Failure(e)
    }
}

fun DocumentSnapshot.toUser() = User(
    id = id,
    nome = getString("nome") ?: "",
    telefone = getString("telefone") ?: "",
    email = getString("email") ?: "",
    status = getString("status") ?: "",
    privileged = getBoolean("privileged") ?: false,
    role = getString("role") ?: "",
).apply {
    id = getId()
}