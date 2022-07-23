package com.example.watchout_frontend_kotlin.others

import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import com.example.watchout_frontend_kotlin.R

    fun popupAlertDialog(context:Context,id :Int){
        val view = View.inflate(context, id,null)
        val builder = AlertDialog.Builder(context)
        builder.setView(view)

        val popupDialog = builder.create()
        popupDialog.show()
        popupDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        popupDialog.window?.setGravity(Gravity.CENTER)
        popupDialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        popupDialog.window?.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
        popupDialog.setCancelable(false)
        view.findViewById<View>(R.id.okay_btn).setOnClickListener{
            popupDialog.dismiss()
        }
        popupDialog.show()
    }
