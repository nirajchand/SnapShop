package com.example.individual_project.viewmodel

import com.example.individual_project.model.UserModel
import com.example.individual_project.repository.UserRepository

class UserViewModel (val repo: UserRepository){
    fun register(email: String, password: String, callback: (Boolean, String, String) -> Unit){
        return repo.register(email,password,callback)
    }

    fun addUserDatabase(userID: String , userModel: UserModel, callback: (Boolean, String) -> Unit){
        return repo.addUserDatabase(userID,userModel,callback)
    }

    fun login(email: String,password: String,callback: (Boolean, String) -> Unit){
        return repo.login(email,password,callback)
    }
}