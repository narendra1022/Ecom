package com.example.ecom.activities

import android.content.ContentValues.TAG
import com.example.ecom.databinding.ActivitySignInBinding


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.textView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {

            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()


            if (email.isNotEmpty() && pass.isNotEmpty()) {
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    if (pass.length > 6) {
                        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                            if (it.isSuccessful) {
                                val intent = Intent(this, shopping::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    this,
                                    "Sign In is not successful",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "password length must be greater then 6 digits",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(this, "Enter correct email format", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            val intent = Intent(this, shopping::class.java)
            startActivity(intent)
        }
    }

    companion object {
        const val READ_PERMISSION = 101
    }
}