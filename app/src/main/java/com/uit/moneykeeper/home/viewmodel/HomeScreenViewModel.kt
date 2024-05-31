package com.uit.moneykeeper.home.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.uit.moneykeeper.models.ViModel

class HomeScreenViewModel {
    fun AddNewWallet(Action: String, walletList: List<ViModel>, tenVi: String, soDu: Double) {
        if(Action == "Cancel") return;
        val maxId = walletList.maxOfOrNull { it.id } ?: 0
        val newWallet = ViModel(id = maxId + 1, ten = tenVi, soDu = soDu)
        val updateWallet = walletList + newWallet
        println("new wallets: " + updateWallet)
//        GlobalObject.updateListVi(updateWallet)
        pushViToFirebase(newWallet)
    }

    fun pushViToFirebase(vi: ViModel) {
        val db = Firebase.firestore

        db.collection("vi")
            .add(vi)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
}