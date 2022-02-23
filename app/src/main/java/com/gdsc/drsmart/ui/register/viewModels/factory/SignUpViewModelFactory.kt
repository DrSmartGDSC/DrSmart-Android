package com.gdsc.drsmart.ui.register.viewModels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gdsc.drsmart.ui.register.repo.RegisterRepository
import com.gdsc.drsmart.ui.register.viewModels.SignUpViewModel

class SignUpViewModelFactory constructor(private val repository: RegisterRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            SignUpViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}

