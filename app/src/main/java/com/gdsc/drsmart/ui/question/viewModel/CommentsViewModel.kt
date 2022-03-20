package com.gdsc.drsmart.ui.question.viewModel

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gdsc.drsmart.ui.doctor.models.comment.CommentResponse
import com.gdsc.drsmart.ui.question.models.CreateCommentResponse
import com.gdsc.drsmart.ui.question.models.EndPostResponse
import com.gdsc.drsmart.ui.question.repo.CommentRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentsViewModel(private val repository: CommentRepository) : ViewModel() {
    var commentResponse: MutableLiveData<CommentResponse> = MutableLiveData()
    var createCommentResponse: MutableLiveData<CreateCommentResponse> = MutableLiveData()
    var endPostResponse: MutableLiveData<EndPostResponse> = MutableLiveData()

    fun getComments(
        ctx: Context,
        auth: String,
        postID: Int,
        view: View
    ) {
        view.visibility = View.VISIBLE
        val response = repository.getComments(auth, postID)
        response.enqueue(object : Callback<CommentResponse> {
            override fun onResponse(
                call: Call<CommentResponse>,
                response: Response<CommentResponse>
            ) {
                view.visibility = View.GONE
                if (response.code() == 200) {
                    commentResponse.value = response.body()
                } else {
                    Toast.makeText(ctx, "Server Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                view.visibility = View.GONE
                Toast.makeText(ctx, "Error", Toast.LENGTH_LONG).show()
            }
        })
    }


    fun createComment(
        ctx: Context,
        auth: String,
        postID: Int,
        text: String,
        view: View
    ) {
        view.visibility = View.VISIBLE
        val response = repository.createComment(auth, postID, text)
        response.enqueue(object : Callback<CreateCommentResponse> {
            override fun onResponse(
                call: Call<CreateCommentResponse>,
                response: Response<CreateCommentResponse>
            ) {
                view.visibility = View.GONE
                if (response.code() == 200) {
                    createCommentResponse.value = response.body()
                } else {
                    Toast.makeText(ctx, "Server Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CreateCommentResponse>, t: Throwable) {
                view.visibility = View.GONE
                Toast.makeText(ctx, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    fun createCommentWithPhoto(
        ctx: Context,
        auth: String,
        postID: Int,
        img: MultipartBody.Part,
        text: RequestBody,
        view: View
    ) {
        view.visibility = View.VISIBLE
        val response = repository.createCommentWithPhoto(auth, postID, img, text)
        response.enqueue(object : Callback<CreateCommentResponse> {
            override fun onResponse(
                call: Call<CreateCommentResponse>,
                response: Response<CreateCommentResponse>
            ) {
                view.visibility = View.GONE
                if (response.code() == 200) {
                    createCommentResponse.value = response.body()
                } else {
                    Toast.makeText(ctx, "Server Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CreateCommentResponse>, t: Throwable) {
                view.visibility = View.GONE
                Toast.makeText(ctx, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }


    fun endPost(
        ctx: Context,
        auth: String,
        postID: Int,
        view: View
    ) {
        view.visibility = View.VISIBLE
        val response = repository.endPost(auth, postID)
        response.enqueue(object : Callback<EndPostResponse> {
            override fun onResponse(
                call: Call<EndPostResponse>,
                response: Response<EndPostResponse>
            ) {
                view.visibility = View.GONE
                if (response.code() == 200) {
                    endPostResponse.value = response.body()
                } else {
                    Toast.makeText(ctx, "Server Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<EndPostResponse>, t: Throwable) {
                view.visibility = View.GONE
                Toast.makeText(ctx, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}