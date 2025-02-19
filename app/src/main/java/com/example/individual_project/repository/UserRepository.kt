package com.example.individual_project.repository

import com.example.individual_project.model.UserModel
import javax.security.auth.callback.Callback

interface UserRepository {

    fun register(email: String, password: String, callback: (Boolean, String, String) -> Unit)

    fun addUserDatabase(userID: String , userModel: UserModel, callback: (Boolean, String) -> Unit)

    fun login(email: String,password: String,callback: (Boolean, String) -> Unit)

}