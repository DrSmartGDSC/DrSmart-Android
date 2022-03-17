package com.gdsc.drsmart.ui.register.viewModels

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gdsc.drsmart.ui.register.models.LoginResponse
import com.gdsc.drsmart.ui.register.repo.LoginRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInViewModel(private val repository: LoginRepository) : ViewModel() {
    var signIn: MutableLiveData<LoginResponse> = MutableLiveData()

    fun signIn(ctx: Context, email: String, password: String, view: View) {
        view.visibility = View.VISIBLE
        val response = repository.signIn(email, password)
        response.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                view.visibility = View.GONE
                signIn.value = response.body()
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                view.visibility = View.GONE
                Toast.makeText(ctx, "Server error ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}