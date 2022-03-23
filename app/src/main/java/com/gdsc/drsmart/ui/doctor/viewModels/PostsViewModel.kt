package com.gdsc.drsmart.ui.doctor.viewModels

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gdsc.drsmart.ui.doctor.models.posts.PostsResponse
import com.gdsc.drsmart.ui.doctor.repo.PostRepository
import com.gdsc.drsmart.ui.question.models.AddPostResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsViewModel(private val repository: PostRepository) : ViewModel() {
    var postsResponse: MutableLiveData<PostsResponse> = MutableLiveData()
    var addPostResponse: MutableLiveData<AddPostResponse> = MutableLiveData()

    fun getPosts(
        ctx: Context,
        auth: String,
        pageNum: Int,
        limit: Int,
        view: View
    ) {
        view.visibility = View.VISIBLE
        val response = repository.getPosts(auth, pageNum, limit)
        response.enqueue(object : Callback<PostsResponse> {
            override fun onResponse(
                call: Call<PostsResponse>,
                response: Response<PostsResponse>
            ) {
                view.visibility = View.GONE
                if (response.code() == 200) {
                    postsResponse.value = response.body()
                } else {
                    Toast.makeText(ctx, "Server Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostsResponse>, t: Throwable) {
                view.visibility = View.GONE
                Toast.makeText(ctx, "Error", Toast.LENGTH_LONG).show()
            }
        })
    }


    fun addPost(
        ctx: Context,
        auth: String,
        desc: String,
        fieldId: Int,
        view: View
    ) {
        view.visibility = View.VISIBLE
        val response = repository.addPost(auth, desc, fieldId)
        response.enqueue(object : Callback<AddPostResponse> {
            override fun onResponse(
                call: Call<AddPostResponse>,
                response: Response<AddPostResponse>
            ) {
                view.visibility = View.GONE
                if (response.code() == 200) {
                    addPostResponse.value = response.body()
                } else {
                    Toast.makeText(ctx, "Server Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AddPostResponse>, t: Throwable) {
                view.visibility = View.GONE
                Toast.makeText(ctx, "Error", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun addPostWithPhoto(
        ctx: Context,
        auth: String,
        img: MultipartBody.Part,
        desc: RequestBody,
        fieldId: RequestBody,
        view: View
    ) {
        view.visibility = View.VISIBLE
        val response = repository.addPostWithPhoto(auth, img, desc, fieldId)
        response.enqueue(object : Callback<AddPostResponse> {
            override fun onResponse(
                call: Call<AddPostResponse>,
                response: Response<AddPostResponse>
            ) {
                view.visibility = View.GONE
                if (response.code() == 200) {
                    addPostResponse.value = response.body()
                } else {
                    Toast.makeText(ctx, "Server Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AddPostResponse>, t: Throwable) {
                view.visibility = View.GONE
                Toast.makeText(ctx, "Error", Toast.LENGTH_LONG).show()
            }
        })
    }

}