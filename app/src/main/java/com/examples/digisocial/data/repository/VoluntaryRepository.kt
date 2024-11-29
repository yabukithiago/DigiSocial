package com.examples.digisocial.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.examples.digisocial.data.models.Voluntary
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object VoluntaryRepository {
    private val db by lazy { FirebaseFirestore.getInstance() }

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