package com.gdsc.drsmart.ui.register.repo

import com.gdsc.drsmart.tools.network.RetrofitService

class RegisterRepository(
    private val retrofitService: RetrofitService,
) {
    fun signUp(email: String, password: String, fullName: String, is_doctor: Int, field_id: Int) =
        retrofitService.signUp(email, password, fullName, is_doctor, field_id)

    fun getFields() =
        retrofitService.getFields()
}
