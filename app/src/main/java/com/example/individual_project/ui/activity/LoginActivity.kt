package com.example.individual_project.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.individual_project.R
import com.example.individual_project.databinding.ActivityLoginBinding
import com.example.individual_project.repository.UserRepositoryImpl
import com.example.individual_project.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        binding.loginButton.setOnClickListener {
            val email = binding.userEmail.text.toString()
            val password = binding.userPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            when {
                email == "admin1@gmail.com" && password == "admin123" -> {
                    Toast.makeText(this, "Admin Login successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, ProductDashboardActivity::class.java))
                }
                else -> {
                    userViewModel.login(email, password) { success, message ->
                        if (success) {
                            Toast.makeText(this, "User Login successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@LoginActivity, UserDashboard::class.java))
                        } else {
                            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        binding.signUpBtn.setOnClickListener{
            var intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.forgetBtn.setOnClickListener{
            val intent = Intent(this@LoginActivity, ForgetActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}