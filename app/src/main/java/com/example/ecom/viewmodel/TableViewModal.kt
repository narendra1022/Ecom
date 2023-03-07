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

class TableViewModal @Inject constructor():ViewModel() {

    private val firestore=Firebase.firestore

    private val _table=MutableStateFlow<Resource<List<product>>>(Resource.unspecified())
    val table:StateFlow<Resource<List<product>>> = _table

    init {
        fetchdata()
    }

    private fun fetchdata() {

        viewModelScope.launch {
            _table.emit(Resource.Loading())
        }
        firestore.collection("Products")
            .whereEqualTo("category","Table").get().addOnSuccessListener {result ->
                val list= result.toObjects(product::class.java)

                viewModelScope.launch {
                    _table.emit(Resource.Success(list))
                }

            }

            .addOnFailureListener {
                viewModelScope.launch {
                    _table.emit(Resource.Error(it.message.toString()))
                }
            }

    }


}