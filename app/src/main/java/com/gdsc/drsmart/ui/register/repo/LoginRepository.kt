package com.gdsc.drsmart.ui.register.repo

import com.gdsc.drsmart.tools.network.RetrofitService

class LoginRepository(
    private val retrofitService: RetrofitService,
) {
    fun signIn(email: String, password: String) = retrofitService.signIn(email, password)
}