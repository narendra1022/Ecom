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


class ChairViewModel @Inject constructor():ViewModel(){

    private val firebase=Firebase.firestore

    val _chair=MutableStateFlow<Resource<List<product>>>(Resource.unspecified())
    val chair:StateFlow<Resource<List<product>>> = _chair


    init {
        fetchdata()
    }

    private fun fetchdata() {

        viewModelScope.launch {
            _chair.emit (Resource.Loading())
        }

        firebase.collection("Products")
            .whereEqualTo("category","Reebok").get().addOnSuccessListener { result ->
                val ProductList=result.toObjects(product::class.java)
                viewModelScope.launch {
                    _chair.emit(Resource.Success(ProductList))
                }

            }

            .addOnFailureListener {
                viewModelScope.launch {
                    _chair.emit(Resource.Error(it.message.toString()))
                }
            }
    }


}