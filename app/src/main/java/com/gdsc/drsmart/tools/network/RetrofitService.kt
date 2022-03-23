package com.gdsc.drsmart.tools.network

import com.gdsc.drsmart.ui.doctor.models.comment.CommentResponse
import com.gdsc.drsmart.ui.doctor.models.posts.PostsResponse
import com.gdsc.drsmart.ui.home.models.InfoModel
import com.gdsc.drsmart.ui.home.models.PredictResponse
import com.gdsc.drsmart.ui.question.models.AddPostResponse
import com.gdsc.drsmart.ui.question.models.CreateCommentResponse
import com.gdsc.drsmart.ui.question.models.EndPostResponse
import com.gdsc.drsmart.ui.register.models.FieldsModel
import com.gdsc.drsmart.ui.register.models.LoginResponse
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetrofitService {

    @FormUrlEncoded
    @POST("login")
    fun signIn(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("signup")
    fun signUp(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("full_name") fullName: String,
        @Field("is_doctor") isDoctor: Int,
        @Field("field_id") fieldId: Int
    ): Call<LoginResponse>

    @GET("fields")
    fun getFields(
    ): Call<FieldsModel>

    @Headers(
        "Content-Length: 0",
        "Accept: application/json"
    )
    @Multipart
    @POST("predict")
    fun doPredict(
        @Header("Authorization") auth: String,
        @Part img: MultipartBody.Part,
        @Part("type") type: RequestBody
    ): Call<PredictResponse>

    @GET("posts")
    fun getPosts(
        @Header("Authorization") auth: String,
        @Query("page") pageNum: Int,
        @Query("limit") limit: Int
    ): Call<PostsResponse>

    @GET("posts/{postId}/comments")
    fun getComments(
        @Header("Authorization") auth: String,
        @Path("postId") postId: Int
    ): Call<CommentResponse>

    @Headers(
        "Content-Length: 0",
        "Accept: application/json"
    )
    @FormUrlEncoded
    @POST("posts/{postId}/comments")
    fun createComment(
        @Header("Authorization") auth: String,
        @Path("postId") postId: Int,
        @Field("text") type: String
    ): Call<CreateCommentResponse>

    @Multipart
    @POST("posts/{postId}/comments")
    fun createCommentWithPhoto(
        @Header("Authorization") auth: String,
        @Path("postId") postId: Int,
        @Part img: MultipartBody.Part,
        @Part("text") type: RequestBody
    ): Call<CreateCommentResponse>

    @POST("posts/{postId}/end")
    fun endPost(
        @Header("Authorization") auth: String,
        @Path("postId") postId: Int,
    ): Call<EndPostResponse>

    @FormUrlEncoded
    @POST("posts")
    fun addPost(
        @Header("Authorization") auth: String,
        @Field("desc") desc: String,
        @Field("field_id") field_id: Int
    ): Call<AddPostResponse>

    @Headers(
        "Content-Length: 0",
        "Accept: application/json"
    )
    @Multipart
    @POST("posts")
    fun addPostWithPhoto(
        @Header("Authorization") auth: String,
        @Part img: MultipartBody.Part,
        @Part("desc") desc: RequestBody,
        @Part("field_id") field_id: RequestBody
    ): Call<AddPostResponse>

    @GET("info")
    fun getInfo(
        @Header("Authorization") auth: String,
        @Query("id") id: Int,
        @Query("type") type: Int
    ): Call<InfoModel>

    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance(): RetrofitService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor).build() // for loging
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://dr-smart-1.ew.r.appspot.com/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}
