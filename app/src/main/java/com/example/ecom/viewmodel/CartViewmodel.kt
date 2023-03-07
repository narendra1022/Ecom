package com.example.ecom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecom.data.cartadata
import com.example.ecom.data.product
import com.example.ecom.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewmodel @Inject constructor(

) : ViewModel() {

    private val firebase=Firebase.firestore
    private val auth= FirebaseAuth.getInstance()

    val _specialproducts= MutableStateFlow<Resource<List<cartadata>>>(Resource.unspecified())
    val specialproduct : StateFlow<Resource<List<cartadata>>> = _specialproducts

    init {
        getCartProducts()
    }


    private fun getCartProducts() {
        viewModelScope.launch {
            _specialproducts.emit (Resource.Loading())
        }
        firebase.collection (  "users")
            .document(auth.uid!!).collection("cart").get().addOnSuccessListener{ result ->
                val sp = result.toObjects(cartadata::class.java)
                viewModelScope.launch {
                    _specialproducts.emit (Resource.Success(sp))
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _specialproducts.emit(Resource.Error(it.message.toString()))
                }
            }
    }


}