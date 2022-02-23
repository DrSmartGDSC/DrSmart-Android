package com.gdsc.drsmart.tools.network

import com.gdsc.drsmart.ui.home.models.PredictResponse
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
        @Field("full_name") fullName: String
    ): Call<LoginResponse>

    //    @Headers("Content-Type:application/json")


    @Headers(
        "Content-Length: 0",
        "Accept: application/json"
    )
    @Multipart
    @POST("predict")
    fun doPredict(
        @Header("Authorization") auth: String,
//        @Part("img\"; filename=\"lol.png\" ") photo: RequestBody,
        @Part img: MultipartBody.Part,
        @Part("type") type: RequestBody
    ): Call<PredictResponse>

    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance(): RetrofitService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor).build() // for loging
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://102.185.123.13:5000/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}
