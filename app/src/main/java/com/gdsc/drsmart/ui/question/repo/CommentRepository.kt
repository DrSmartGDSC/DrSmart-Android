package com.gdsc.drsmart.ui.question.repo

import com.gdsc.drsmart.tools.network.RetrofitService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CommentRepository(
    private val retrofitService: RetrofitService,
) {
    fun getComments(auth: String, postId: Int) =
        retrofitService.getComments(auth, postId)

    fun createComment(auth: String, postId: Int, text: String) =
        retrofitService.createComment(auth, postId, text)

    fun createCommentWithPhoto(
        auth: String,
        postId: Int,
        img: MultipartBody.Part,
        text: RequestBody
    ) =
        retrofitService.createCommentWithPhoto(auth, postId, img, text)

    fun endPost(auth: String, postId: Int) =
        retrofitService.endPost(auth, postId)
}
