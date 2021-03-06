package com.gdsc.drsmart.ui.register.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gdsc.drsmart.MainActivity
import com.gdsc.drsmart.R
import com.gdsc.drsmart.tools.network.RetrofitService
import com.gdsc.drsmart.tools.storage.AppReferences
import com.gdsc.drsmart.ui.doctor.activities.DoctorHomePage
import com.gdsc.drsmart.ui.register.repo.LoginRepository
import com.gdsc.drsmart.ui.register.viewModels.SignInViewModel
import com.gdsc.drsmart.ui.register.viewModels.factory.SignInViewModelFactory
import kotlinx.android.synthetic.main.activity_sign_in.*
import java.util.*

lateinit var viewModel: SignInViewModel
lateinit var retrofitService: RetrofitService

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        initView()
    }

    private fun initView() {
        retrofitService = RetrofitService.getInstance(this)
        viewModel = ViewModelProvider(
            this, SignInViewModelFactory(
                LoginRepository(
                    retrofitService
                )
            )
        )[SignInViewModel::class.java]

        backBtn.setOnClickListener { finish() }
        //handle sign in
        patientBtn.setOnClickListener {
            val email = emailEditText.text.toString().lowercase(Locale.getDefault())
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty()
                && password.isNotEmpty()
            ) {
                signIn(email, password)
            } else {
                Toast.makeText(this, getString(R.string.please_fill_all_fields), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun signIn(email: String, password: String) {
        viewModel.signIn(this, email.trim(), password, progress)

        viewModel.signIn.observe(this, {
            if (it.status) {
                if (!it.user.is_doctor) {
                    val i = Intent(this, MainActivity::class.java)
                    AppReferences.setLoginState(this, true)
                    AppReferences.setDocLoginState(this, false)
                    startActivity(i)
                } else {
                    val i = Intent(this, DoctorHomePage::class.java)
                    AppReferences.setDocLoginState(this, true)
                    AppReferences.setLoginState(this, false)
                    startActivity(i)
                }
                AppReferences.setUserId(this, it.user.user_id.toString())
                AppReferences.setToken(this, "Bearer " + it.token)
            } else {
                Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
            }
        })
    }
}