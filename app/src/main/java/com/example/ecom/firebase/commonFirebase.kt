package com.example.ecom.firebase

import com.example.ecom.data.CartProduct
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class commonFirebase(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    private val cart =
        firestore.collection("users").document(auth.currentUser!!.uid).collection("cart")

    fun adding(cartProduct: CartProduct, onResult: (CartProduct?, Exception?) -> Unit) {
        cart.document().set(cartProduct)
            .addOnSuccessListener {
                onResult(cartProduct, null)
            }
            .addOnFailureListener {
                onResult(null, it)
            }
    }

    fun increse(documentId: String, onResult: (String?, Exception?) -> Unit) {
        firestore.runTransaction { transaction ->
            val document = transaction.get(cart.document(documentId))
            val productObject = document.toObject(CartProduct::class.java)
            productObject?.let { cartProduct ->
                val quantity = cartProduct.quantity + 1
                val newproductObject = cartProduct.copy(quantity = quantity)
                transaction.set(cart.document(documentId), newproductObject)
            }
        }.addOnSuccessListener {
            onResult(documentId, null)
        }.addOnFailureListener {
            onResult(null, it)
        }
    }

}