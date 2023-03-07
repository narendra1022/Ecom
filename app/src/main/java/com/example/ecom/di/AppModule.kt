package com.example.ecom.di

import com.example.ecom.firebase.commonFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestoreDatabse() = Firebase.firestore

    @Provides
    @Singleton
    fun providecommonfirebase(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    )=commonFirebase(firestore,firebaseAuth)
}