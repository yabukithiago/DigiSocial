package com.examples.digisocial.data.repository

import com.examples.digisocial.domain.models.Beneficiary
import com.examples.digisocial.domain.models.Response
import com.examples.digisocial.domain.models.Visit
import com.examples.digisocial.domain.repository.BeneficiaryRepository
import com.examples.digisocial.domain.repository.BeneficiaryResponse
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class BeneficiaryRepositoryImpl(private val beneficiaryRef: CollectionReference) : BeneficiaryRepository {
    override fun getBeneficiary() = callbackFlow {
        val listener = beneficiaryRef.addSnapshotListener { lista, e ->
            val listaBeneficiaryResponse = if (lista != null) {
                val listaBeneficiary = lista.map { beneficiarySnapshot ->
                    beneficiarySnapshot.toBeneficiary()
                }
                Response.Success(listaBeneficiary)
            } else {
                Response.Failure(e)
            }
            trySend(listaBeneficiaryResponse)
        }
        awaitClose {
            listener.remove()
        }
    }

    override suspend fun getBeneficiaryById(beneficiaryId: String): BeneficiaryResponse {
        return try{
            val beneficiarySnapshot = beneficiaryRef.document(beneficiaryId).get().await()
            val beneficiary = beneficiarySnapshot.toBeneficiary()
            val visitasSnapshot = beneficiaryRef.document(beneficiaryId).collection("visits").get().await()
            val visitas = visitasSnapshot.documents.map { it.toObject(Visit::class.java)!! }

            Response.Success(beneficiary.copy(visitas = visitas))
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun addBeneficiary(beneficiary: Beneficiary): Response<String> {
        return try {
            val id = beneficiaryRef.document().id
            val beneficiaryId = beneficiary.copy(id = id)

            beneficiaryRef.document(id).set(beneficiaryId).await()
            Response.Success(id)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun updateBeneficiary(beneficiary: Beneficiary) = try {
        val void = beneficiary.id.let { id ->
            beneficiaryRef.document(id).update(
                mapOf(
                    "nome" to beneficiary.nome,
                    "telemovel" to beneficiary.telemovel,
                    "referencia" to beneficiary.referencia,
                    "agregadoFamiliar" to beneficiary.agregadoFamiliar,
                    "nacionalidade" to beneficiary.nacionalidade,
                    "pedidos" to beneficiary.pedidos,
                    "numeroVisitas" to beneficiary.numeroVisitas
                )
            ).await()
        }
        Response.Success(void)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun deleteBeneficiary(id: String) = try {
        val void = beneficiaryRef.document(id).delete().await()
        Response.Success(void)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun addVisitToBeneficiary(
        beneficiaryId: String,
        visit: Visit
    ): Response<String> {
        return try {
            val visitRef = beneficiaryRef.document(beneficiaryId).collection("visits")
            val visitId = visitRef.document().id

            val visitWithId = visit.copy(id = visitId)
            visitRef.document(visitId).set(visitWithId).await()
            Response.Success(visitId)
        } catch (e:Exception){
            Response.Failure(e)
        }
    }
}

fun DocumentSnapshot.toBeneficiary() = Beneficiary(
    id = id,
    nome = getString("nome") ?: "",
    telemovel = getString("telemovel") ?: "",
    referencia = getString("referencia") ?: "",
    agregadoFamiliar = getLong("agregadoFamiliar") ?: 0,
    nacionalidade = getString("nacionalidade") ?: "",
    pedidos = getString("pedidos") ?: "",
    numeroVisitas = getLong("numeroVisitas") ?: 0,
    ownerId = getString("ownerId") ?: "",
).apply {
    id = getId()
}