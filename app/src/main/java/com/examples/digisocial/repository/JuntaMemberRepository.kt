package com.examples.digisocial.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.examples.digisocial.data.models.JuntaMember
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object JuntaMemberRepository {
    private val db by lazy { FirebaseFirestore.getInstance() }

    fun createJuntaMember(  email: String,
                            password: String,
                            nome: String,
                            telefone: String,
                            onSuccess: (String) -> Unit,
                            onFailure: (String) -> Unit) {
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
                        val juntaMember = JuntaMember(id = userId, email = email,
                            telefone = telefone, nome = nome)
                        db.collection("user").document(userId)
                            .set(juntaMember)
                            .addOnSuccessListener {
                                auth.signOut()
                                auth.signInWithEmailAndPassword(adminEmail!!, adminPassword)
                                    .addOnCompleteListener { adminLoginTask ->
                                        if (adminLoginTask.isSuccessful) {
                                            onSuccess("Membro da Junta criado com sucesso e sessão do admin restaurada")
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
                    onFailure("Erro ao criar membro da junta: ${task.exception?.message}")
                }
            }
    }

    fun getAll(onSuccess: (List<JuntaMember>) -> Unit) {
        db.collection("user")
            .whereEqualTo("role", "juntamember")
            .addSnapshotListener { value, error ->
                if(error != null){
                    Log.w(TAG, "Listen failed.", error)
                    return@addSnapshotListener
                }

                val listJuntaMember = mutableListOf<JuntaMember>()
                value?.let {
                    for (document in it.documents) {
                        document.data?.let { data ->
                            listJuntaMember.add(JuntaMember.fromMap(data))
                        }
                    }
                }
                onSuccess(listJuntaMember)
            }
    }
}