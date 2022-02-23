package com.gdsc.drsmart.ui.register.models

data class LoginResponse(
    val email: String,
    val status: Boolean,
    val token: String,
    val error: String
)