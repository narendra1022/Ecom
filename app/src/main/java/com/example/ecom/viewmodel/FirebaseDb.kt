package com.example.ecom.viewmodel

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseDb {
    private val productsCollection = Firebase.firestore.collection("Products")

//    private val firebaseStorage = Firebase.storage.reference
//    val userUid = FirebaseAuth.getInstance().currentUser?.uid

    fun searchProducts(searchQuery: String) = productsCollection
        .orderBy("title")
        .startAt(searchQuery)
        .endAt(searchQuery + "\uf8ff")
        .limit(5)
        .get()


}
