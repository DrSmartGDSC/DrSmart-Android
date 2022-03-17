package com.gdsc.drsmart.tools.storage

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object AppReferences {
    fun setLoginState(context: Activity?, state: Boolean) {
        val sharedPreferences: SharedPreferences =
            context!!.getSharedPreferences("login", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean("login", state)
        editor.apply()
    }

    fun getLoginState(context: Activity?): Boolean {
        val sharedPreferences: SharedPreferences =
            context!!.getSharedPreferences("login", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("login", false)
    }

    fun setDocLoginState(context: Activity?, state: Boolean) {
        val sharedPreferences: SharedPreferences =
            context!!.getSharedPreferences("doc_login", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean("doc_login", state)
        editor.apply()
    }

    fun getDocLoginState(context: Activity?): Boolean {
        val sharedPreferences: SharedPreferences =
            context!!.getSharedPreferences("doc_login", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("doc_login", false)
    }

    fun setToken(context: Activity?, state: String) {
        val sharedPreferences: SharedPreferences =
            context!!.getSharedPreferences("token", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("token", state)
        editor.apply()
    }

    fun getToken(context: Activity?): String {
        val sharedPreferences: SharedPreferences =
            context!!.getSharedPreferences("token", Context.MODE_PRIVATE)
        return sharedPreferences.getString("token", "")!!
    }

}