package com.gdsc.drsmart.ui.register.repo

import com.gdsc.drsmart.tools.network.RetrofitService

class RegisterRepository(
    private val retrofitService: RetrofitService,
) {
    fun signUp(email: String, password: String, fullName: String) =
        retrofitService.signUp(email, password, fullName)
}
