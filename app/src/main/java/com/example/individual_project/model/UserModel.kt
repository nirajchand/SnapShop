package com.example.individual_project.model

import android.os.Parcelable

data class UserModel(
    var userID : String =  "",
    var firstName : String = "",
    var lastName : String = "",
    var address : String = "",
    var phoneNumber : String = "",
    var email: String = ""
) {

}