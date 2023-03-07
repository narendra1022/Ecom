package com.example.ecom.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ecom.data.User
import com.example.ecom.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel

class RegisterViewmodel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _register = MutableStateFlow<Resource<FirebaseUser>>(Resource.unspecified())
    val register: Flow<Resource<FirebaseUser>> = _register

    fun createaccountwithemailandpassword(user: User, password: String) {
        runBlocking{
            _register.emit(Resource.Loading())
        }
        firebaseAuth.createUserWithEmailAndPassword(user.name, password)
            .addOnSuccessListener {
                it.user?.let {
                    _register.value=Resource.Success(it)
                }
            }
            .addOnFailureListener {
                _register.value=Resource.Error(it.message.toString())
            }

    }
}