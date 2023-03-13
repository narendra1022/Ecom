package com.example.ecom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecom.data.product
import com.example.ecom.util.Resource
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CupboardViewModal @Inject constructor() :ViewModel() {

    private val firebase= Firebase.firestore

    val _cup= MutableStateFlow<Resource<List<product>>>(Resource.unspecified())
    val cup: StateFlow<Resource<List<product>>> = _cup


    init {
        fetchdat()
    }

    private fun fetchdat() {

        viewModelScope.launch {
            _cup.emit (Resource.Loading())
        }


        firebase.collection("Products")
            .whereEqualTo("category","Puma").get().addOnSuccessListener { result ->
                val Pro=result.toObjects(product::class.java)
                viewModelScope.launch {
                    _cup.emit(Resource.Success(Pro))
                }

            }

            .addOnFailureListener {
                viewModelScope.launch {
                    _cup.emit(Resource.Error(it.message.toString()))
                }
            }
    }




}