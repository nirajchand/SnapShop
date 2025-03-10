package com.example.individual_project.utils

import android.app.Activity
import android.app.AlertDialog
import com.example.individual_project.R

    class LoadingUtils(val activity: Activity) {
        lateinit var alertDialog: AlertDialog

        fun show(){
            val dialogView = activity.layoutInflater.inflate(R.layout.loading,null)

            val builder = AlertDialog.Builder(activity)
            builder.setView(dialogView)
            builder.setCancelable(false)

            alertDialog = builder.create()
            alertDialog.show()
        }

        fun dismiss(){
            alertDialog.dismiss()
        }
    }