package com.examples.digisocial.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.examples.digisocial.data.models.Beneficiary
import com.google.firebase.firestore.FirebaseFirestore

object BeneficiaryRepository {
    val db by lazy { FirebaseFirestore.getInstance() }

    fun getAll(onSuccess: (List<Beneficiary>) -> Unit) {
        db.collection("beneficiary")
            .addSnapshotListener { value, error ->
                if(error != null){
                    Log.w(TAG, "Listen failed.", error)
                    return@addSnapshotListener
                }

                val listBeneficiary = mutableListOf<Beneficiary>()
                value?.let {
                    for (document in it.documents) {
                        document.data?.let { data ->
                            listBeneficiary.add(Beneficiary.fromMap(data))
                        }
                    }
                }
                onSuccess(listBeneficiary)
            }
    }
}