package com.gdsc.drsmart.ui.home.viewModels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gdsc.drsmart.ui.home.repo.ScanRepository
import com.gdsc.drsmart.ui.home.viewModels.ScanViewModel

class ScanViewModelFactory constructor(private val repository: ScanRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ScanViewModel::class.java)) {
            ScanViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}

