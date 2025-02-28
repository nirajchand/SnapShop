package com.example.individual_project.repository

import android.util.Log
import com.example.individual_project.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlin.math.log

class UserRepositoryImpl: UserRepository {
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var reference = database.reference.child("users")

    override fun register(email: String, password: String, callback: (Boolean, String, String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userId = auth.currentUser?.uid
                if (userId != null) {
                    callback(true, "Registration Successful", userId)
                } else {
                    callback(false, "Failed to retrieve User ID", "")
                }
            } else {
                callback(false, task.exception?.message ?: "Unknown error", "")
            }
        }
    }

    override fun addUserDatabase(userID: String, userModel: UserModel, callback: (Boolean, String) -> Unit) {
        reference.child(userID).setValue(userModel).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(true, "User added successfully to database")
            } else {
                callback(false, task.exception?.message ?: "Unknown database error")
            }
        }
    }

    override fun login(
        email: String,
        password: String,
        callback: (Boolean, String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
            if (it.isSuccessful){
                callback(true,"Login successfully")
            }else{
                callback(false,it.exception?.message.toString())
            }
        }
    }

    override fun forgetPassword(
        email: String,
        callback: (Boolean, String) -> Unit
    ) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener{
            if (it.isSuccessful){
                callback(true,"Reset link sent to your email")
            }else{
                callback(false,it.exception?.message.toString())
            }
        }
    }


}