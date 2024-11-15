package com.examples.digisocial.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.examples.digisocial.data.models.Voluntary
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object VoluntaryRepository {
    private val db by lazy { FirebaseFirestore.getInstance() }

    fun createVoluntary(email: String, password: String, nome: String,
                        telefone: String, isPrivileged: Boolean,
                        onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val currentUser = auth.currentUser
        val adminEmail = currentUser?.email
        val adminPassword = "123456"

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = task.result?.user?.uid
                    if (userId != null) {
                        val voluntary = Voluntary(id = userId, email = email,
                            telefone = telefone, nome = nome, privileged = isPrivileged)
                        db.collection("user").document(userId)
                            .set(voluntary)
                            .addOnSuccessListener {
                                auth.signOut()
                                auth.signInWithEmailAndPassword(adminEmail!!, adminPassword)
                                    .addOnCompleteListener { adminLoginTask ->
                                        if (adminLoginTask.isSuccessful) {
                                            onSuccess("Voluntário criado com sucesso e sessão do admin restaurada")
                                        } else {
                                            onFailure("Erro ao restaurar a conta admin: ${adminLoginTask.exception?.message}")
                                        }
                                    }
                            }
                            .addOnFailureListener { e ->
                                onFailure("Erro ao registrar no Firestore: ${e.message}")
                            }
                    }
                } else {
                    onFailure("Erro ao criar voluntário: ${task.exception?.message}")
                }
            }
    }

    fun getAll(onSuccess: (List<Voluntary>) -> Unit) {
        db.collection("user")
            .whereEqualTo("role", "voluntary")
            .addSnapshotListener { value, error ->
                if(error != null){
                    Log.w(TAG, "Listen failed.", error)
                    return@addSnapshotListener
                }

                val listVoluntary = mutableListOf<Voluntary>()
                value?.let {
                    for (document in it.documents) {
                        document.data?.let { data ->
                            listVoluntary.add(Voluntary.fromMap(data))
                        }
                    }
                }
                onSuccess(listVoluntary)
            }
    }
}