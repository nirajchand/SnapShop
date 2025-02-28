package com.example.individual_project.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.individual_project.R
import com.example.individual_project.databinding.ActivityForgetBinding
import com.example.individual_project.repository.UserRepositoryImpl
import com.example.individual_project.viewmodel.UserViewModel

class ForgetActivity : AppCompatActivity() {

    lateinit var binding: ActivityForgetBinding
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityForgetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        binding.btnResetPassword.setOnClickListener{
            val email = binding.etEmail.text.toString()

            userViewModel.forgetPassword(email){
                success,message ->
                if (success){
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ForgetActivity, LoginActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}