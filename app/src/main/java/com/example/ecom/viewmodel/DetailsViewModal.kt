package com.example.ecom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecom.data.CartProduct
import com.example.ecom.firebase.commonFirebase
import com.example.ecom.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class DetailsViewModal @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val commonFirebase: commonFirebase
) : ViewModel() {

    private val _addtoCart = MutableStateFlow<Resource<CartProduct>>(Resource.unspecified())
    val addtocart = _addtoCart.asStateFlow()


    fun updateproductincart(cartProduct: CartProduct) {
        viewModelScope.launch { _addtoCart.emit(Resource.Loading()) }

        firestore.collection("users").document(auth.currentUser!!.uid).collection("cart")
            .get().addOnSuccessListener {
                    addingProduct(cartProduct)
            }.addOnFailureListener {
                viewModelScope.launch { _addtoCart.emit(Resource.Error(it.message.toString())) }
            }
    }

    private fun addingProduct(cartProduct: CartProduct) {
        commonFirebase.adding(cartProduct) { addedProduct, e ->
            viewModelScope.launch {
                if (e == null)
                    _addtoCart.emit(Resource.Success(addedProduct!!))
                else
                    _addtoCart.emit(Resource.Error(e.message.toString()))
            }
        }
    }

    private fun increaseProduct(documentId: String, cartProduct: CartProduct) {
        commonFirebase.increse(documentId) { _, e ->
            viewModelScope.launch {
                if (e == null)
                    _addtoCart.emit(Resource.Success(cartProduct))
                else
                    _addtoCart.emit(Resource.Error(e.message.toString()))
            }
        }
    }

}