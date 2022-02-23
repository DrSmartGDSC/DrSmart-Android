package com.gdsc.drsmart.ui.register.viewModels

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gdsc.drsmart.ui.register.models.LoginResponse
import com.gdsc.drsmart.ui.register.repo.RegisterRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel(private val repository: RegisterRepository) : ViewModel() {
    var signUp: MutableLiveData<LoginResponse> = MutableLiveData()

    fun signUp(ctx: Context, email: String, password: String, fullName: String, view: View) {
        view.visibility = View.VISIBLE
        val response = repository.signUp(email, password, fullName)
        response.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                view.visibility = View.GONE
                signUp.value = response.body()
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                view.visibility = View.GONE
                Toast.makeText(ctx, "Error", Toast.LENGTH_LONG).show()

            }
        })
    }
}