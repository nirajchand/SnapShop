package com.example.individual_project.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.individual_project.R
import com.example.individual_project.databinding.ActivityRegisterBinding
import com.example.individual_project.model.UserModel
import com.example.individual_project.repository.UserRepositoryImpl
import com.example.individual_project.viewmodel.UserViewModel
import kotlin.toString

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding

    lateinit var userViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        binding.signUp.setOnClickListener{
            var email = binding.registerEmail.text.toString()
            var password = binding.registerPassword.text.toString()
            var fName  = binding.registerFname.text.toString()
            var lName = binding.registerLName.text.toString()
            var address = binding.registerAddress.text.toString()
            var contact = binding.registerContact.text.toString()

            userViewModel.register(email, password) { success, message, userID ->
                if (success) {
                    val userModel = UserModel(
                        userID,
                        fName, lName, address, contact, email
                    )
                    addUser(userModel)
                } else {
                    Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.backToLogin.setOnClickListener{
            val intent = Intent(this@RegisterActivity, LoginActivity :: class.java)
            startActivity(intent)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun addUser(userModel: UserModel) {
        userViewModel.addUserDatabase(userModel.userID, userModel) { success, message ->
            runOnUiThread {
                if (success) {
                    Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_LONG).show()

                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, "Error: $message", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

