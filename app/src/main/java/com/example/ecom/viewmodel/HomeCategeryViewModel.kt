package com.example.ecom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecom.data.product
import com.example.ecom.util.Resource
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel

class HomeCategeryViewModel @Inject constructor() :ViewModel() {

    private val firebase=Firebase.firestore

    val _specialproducts= MutableStateFlow<Resource<List<product>>>(Resource.unspecified())
    val specialproduct : StateFlow<Resource<List<product>>> = _specialproducts

    val _bestdealproducts= MutableStateFlow<Resource<List<product>>>(Resource.unspecified())
    val bestdealproducts : StateFlow<Resource<List<product>>> = _bestdealproducts

    val _bestproducts= MutableStateFlow<Resource<List<product>>>(Resource.unspecified())
    val bestproducts : StateFlow<Resource<List<product>>> = _bestproducts

init {
    fetchSpecialProducts()
    fetchBestDealProducts()
    fetchBestProducts()
}

    private fun fetchBestProducts() {

        viewModelScope.launch {
            _bestproducts.emit (Resource.Loading())
        }
        firebase.collection (  "Products")
            .whereEqualTo(  "category", "Best Products").get().addOnSuccessListener{ result ->
                val ProductsList = result.toObjects(product::class.java)
                viewModelScope.launch {
                    _bestproducts.emit(Resource.Success(ProductsList))
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _bestproducts.emit(Resource.Error(it.message.toString()))
                }
            }

    }

    private fun fetchBestDealProducts() {

        viewModelScope.launch {
            _bestdealproducts.emit (Resource.Loading())
        }
        firebase.collection (  "Products")
            .whereEqualTo(  "category", "Best Deal Products").get().addOnSuccessListener{ result ->
                val List = result.toObjects(product::class.java)
                viewModelScope.launch {
                    _bestdealproducts.emit (Resource.Success(List))
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _bestdealproducts.emit(Resource.Error(it.message.toString()))
                }
            }

    }

    fun fetchSpecialProducts() {
        viewModelScope.launch {
            _specialproducts.emit (Resource.Loading())
        }
        firebase.collection (  "Products")
        .whereEqualTo(  "category", "Special Products").get().addOnSuccessListener{ result ->
            val specialProductsList = result.toObjects(product::class.java)
            viewModelScope.launch {
                _specialproducts.emit (Resource.Success(specialProductsList))
            }
        }.addOnFailureListener {
            viewModelScope.launch {
                _specialproducts.emit(Resource.Error(it.message.toString()))
            }
        }
}}