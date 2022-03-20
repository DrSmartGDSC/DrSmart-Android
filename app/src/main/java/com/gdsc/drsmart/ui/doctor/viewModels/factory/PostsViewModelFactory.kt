package com.gdsc.drsmart.ui.doctor.viewModels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gdsc.drsmart.ui.doctor.repo.PostRepository
import com.gdsc.drsmart.ui.doctor.viewModels.PostsViewModel

class PostsViewModelFactory constructor(private val repository: PostRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PostsViewModel::class.java)) {
            PostsViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}

