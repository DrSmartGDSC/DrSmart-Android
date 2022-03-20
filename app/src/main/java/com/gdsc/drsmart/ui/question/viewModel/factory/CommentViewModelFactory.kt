package com.gdsc.drsmart.ui.question.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gdsc.drsmart.ui.question.repo.CommentRepository
import com.gdsc.drsmart.ui.question.viewModel.CommentsViewModel

class CommentViewModelFactory constructor(private val repository: CommentRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CommentsViewModel::class.java)) {
            CommentsViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}

