package com.examples.digisocial.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.examples.digisocial.data.models.Voluntary
import com.google.firebase.firestore.FirebaseFirestore

object VoluntaryRepository {
    val db by lazy { FirebaseFirestore.getInstance() }

    fun addVoluntary(voluntary: Voluntary, onAddVoluntarySuccess: () -> Unit){
        db.collection("user")
            .add(voluntary)
            .addOnSuccessListener { voluntario ->
                Log.d(TAG, "Voluntary added with ID: ${voluntario.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding voluntary", e)
            }
    }

    fun getVoluntaryByRole(role: String, onGetVoluntarySuccess: (Voluntary) -> Unit){
        db.collection("user")
            .document(role)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    onGetVoluntarySuccess(document.toObject(Voluntary::class.java)!!)
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }
}