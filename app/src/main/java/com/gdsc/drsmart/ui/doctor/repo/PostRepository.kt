package com.gdsc.drsmart.ui.doctor.repo

import com.gdsc.drsmart.tools.network.RetrofitService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class PostRepository(
    private val retrofitService: RetrofitService,
) {
    fun getPosts(auth: String, pageNum: Int, limit: Int) =
        retrofitService.getPosts(auth, pageNum, limit)

    fun addPost(auth: String, desc: String, fieldId: Int) =
        retrofitService.addPost(auth, desc, fieldId)

    fun addPostWithPhoto(
        auth: String,
        img: MultipartBody.Part,
        desc: RequestBody,
        fieldId: RequestBody,
    ) =
        retrofitService.addPostWithPhoto(auth, img, desc, fieldId)
}
