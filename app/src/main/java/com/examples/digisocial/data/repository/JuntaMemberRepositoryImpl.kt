package com.examples.digisocial.data.repository

import com.examples.digisocial.domain.models.JuntaMember
import com.examples.digisocial.domain.models.Response
import com.examples.digisocial.domain.repository.JuntaMemberRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class JuntaMemberRepositoryImpl(private val juntaMemberRef: CollectionReference) : JuntaMemberRepository {
    override fun getJuntaMember() = callbackFlow {
        val listener = juntaMemberRef
            .whereEqualTo("role", "juntamember")
            .whereEqualTo("status", "ativo")
            .addSnapshotListener { lista, e ->
                val listaJuntaMemberResponse = if (lista != null) {
                    val listaJuntaMember = lista.map { juntaMemberSnapshot ->
                        juntaMemberSnapshot.toJuntaMember()
                    }
                    Response.Success(listaJuntaMember)
                } else {
                    Response.Failure(e)
                }
                trySend(listaJuntaMemberResponse)
            }
        awaitClose {
            listener.remove()
        }
    }

    override suspend fun getJuntaMemberById(id: String)= try {
        val juntaMemberSnapshot = juntaMemberRef.document(id).get().await()
        val juntaMember = if (juntaMemberSnapshot.getString("role") == "juntamember") {
            juntaMemberSnapshot.toObject(JuntaMember::class.java)
        } else null

        Response.Success(juntaMember)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun deleteJuntaMember(id: String) = try {
        val void = juntaMemberRef.document(id).update("status", "inativo").await()
        Response.Success(void)
    } catch (e: Exception) {
        Response.Failure(e)
    }
}

fun DocumentSnapshot.toJuntaMember() = JuntaMember(
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