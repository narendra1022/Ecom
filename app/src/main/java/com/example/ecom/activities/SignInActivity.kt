package com.example.ecom.activities


import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import com.example.ecom.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay


class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)

        WindowCompat.setDecorFitsSystemWindows(window, true)
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView)

        windowInsetsController?.isAppearanceLightNavigationBars = true
        windowInsetsController?.isAppearanceLightStatusBars = true

        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.signupBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.signinBtn.setOnClickListener {

            binding.signinBtn.startAnimation()

            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()


            if (email.isNotEmpty() && pass.isNotEmpty()) {
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    if (pass.length > 6) {
                        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                            if (it.isSuccessful) {
                                binding.signinBtn.revertAnimation()
                                val intent = Intent(this, shopping::class.java)
                                startActivity(intent)
                            } else {
                                binding.signinBtn.revertAnimation()
                                Toast.makeText(
                                    this,
                                    "Sign In is not successful",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        }
                    } else {
                        binding.signinBtn.revertAnimation()
                        Toast.makeText(
                            this,
                            "password length must be greater then 6 digits",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    binding.signinBtn.revertAnimation()
                    Toast.makeText(this, "Enter correct email format", Toast.LENGTH_SHORT).show()
                }

            } else {
                binding.signinBtn.revertAnimation()
                Toast.makeText(this, "Empty Fields are not allowed !!", Toast.LENGTH_SHORT).show()
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