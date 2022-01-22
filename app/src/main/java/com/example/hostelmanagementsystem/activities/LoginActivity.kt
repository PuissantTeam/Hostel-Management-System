package com.example.hostelmanagementsystem.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.hostelmanagementsystem.Data.Prefs
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.databinding.ActivityLoginBinding
import com.example.hostelmanagementsystem.databinding.ActivityRegisterBinding
import com.example.hostelmanagementsystem.extensions.showSnackBar
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        binding.signinWithEmailButton.setOnClickListener(View.OnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            if (email.isEmpty()) {
                binding.emailEditText.error = "Enter email"
                return@OnClickListener
            }
            if (password.isEmpty()) {
                binding.passwordEditText.error = "Enter Password"
                return@OnClickListener
            }
            val mAuth = FirebaseAuth.getInstance()
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                OnCompleteListener {
                    if (it.isSuccessful) {
                        val user = FirebaseAuth.getInstance().currentUser
                        FirebaseFirestore.getInstance().collection("User").document(user!!.uid)
                            .get().addOnCompleteListener(
                                OnCompleteListener {
                                    if (!it.result.exists()) {
                                        showSnackBar(
                                            this,
                                            "The user does not exist",
                                            binding.loginBottom
                                        )
                                        mAuth.signOut()
                                        return@OnCompleteListener
                                    }
                                    val userType = it.result.getString("userType")
                                    val prefs = Prefs(this)
                                    prefs.status = 1
                                    if (userType.equals("admin")) {
                                        prefs.userType = "admin"
                                        showSnackBar(this, "Login Successful", binding.loginBottom)
                                        val intent = Intent(this, AdminActivity::class.java)
                                        startActivity(intent)
                                    }
                                    if (userType.equals("warden")) {
                                        prefs.userType = "warden"
                                        val intent = Intent(this, WardenActivity::class.java)
                                        startActivity(intent)
                                    }
                                    if (userType.equals("student")) {
                                        prefs.userType = "student"
                                        val intent = Intent(this, StudentActivity::class.java)
                                        startActivity(intent)
                                    }
                                })
                    }
                    else{
                        showSnackBar(this, "Something went wrong", binding.loginBottom)
                    }
                }).addOnFailureListener {
                showSnackBar(this, it.toString(), view)
                Log.d("test", it.toString())
            }
        })


        setContentView(view)
    }
}