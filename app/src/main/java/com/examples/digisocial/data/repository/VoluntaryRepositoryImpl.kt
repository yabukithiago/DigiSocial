package com.examples.digisocial.data.repository

import com.examples.digisocial.domain.models.Response
import com.examples.digisocial.domain.models.Voluntary
import com.examples.digisocial.domain.repository.VoluntaryRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class VoluntaryRepositoryImpl(private val voluntaryRef: CollectionReference) : VoluntaryRepository {
    override fun getVoluntary() = callbackFlow {
        val listener = voluntaryRef
            .whereEqualTo("role", "voluntary")
            .whereEqualTo("status", "ativo")
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

    override suspend fun getVoluntaryById(id: String) = try {
        val voluntarySnapshot = voluntaryRef.document(id).get().await()
        val voluntary = if (voluntarySnapshot.getString("role") == "voluntary") {
            voluntarySnapshot.toObject(Voluntary::class.java)
        } else null

        Response.Success(voluntary)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun deleteVoluntary(id: String) = try {
        val void = voluntaryRef.document(id).update("status", "inativo").await()
        Response.Success(void)
    } catch (e: Exception) {
        Response.Failure(e)
    }
}

fun DocumentSnapshot.toVoluntary() = Voluntary(
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