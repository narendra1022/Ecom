package com.example.ecom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecom.data.addressData
import com.example.ecom.data.product
import com.example.ecom.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddressLitsViewmodal @Inject constructor(): ViewModel(){

    private val firebase= Firebase.firestore

    val _list= MutableStateFlow<Resource<List<addressData>>>(Resource.unspecified())
    val list: StateFlow<Resource<List<addressData>>> = _list


    init {
        fetchdata()
    }

    private fun fetchdata() {

        viewModelScope.launch {
            _list.emit (Resource.Loading())
        }


        firebase.collection("users").document(FirebaseAuth.getInstance().uid!!).collection("Addresses")
            .get().addOnSuccessListener { result ->
                val ProductList=result.toObjects(addressData::class.java)
                viewModelScope.launch {
                    _list.emit(Resource.Success(ProductList))
                }

            }

            .addOnFailureListener {
                viewModelScope.launch {
                    _list.emit(Resource.Error(it.message.toString()))
                }
            }
    }


}