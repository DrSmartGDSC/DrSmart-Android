package com.gdsc.drsmart.ui.register.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gdsc.drsmart.R
import com.gdsc.drsmart.tools.network.RetrofitService
import com.gdsc.drsmart.ui.register.repo.RegisterRepository
import com.gdsc.drsmart.ui.register.viewModels.SignUpViewModel
import com.gdsc.drsmart.ui.register.viewModels.factory.SignUpViewModelFactory
import kotlinx.android.synthetic.main.activity_sign_in.backBtn
import kotlinx.android.synthetic.main.activity_sign_in.emailEditText
import kotlinx.android.synthetic.main.activity_sign_in.passwordEditText
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    lateinit var viewModel: SignUpViewModel
    private val retrofitService = RetrofitService.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        initView()
    }

    private fun initView() {
        viewModel = ViewModelProvider(
            this, SignUpViewModelFactory(
                RegisterRepository(
                    retrofitService
                )
            )
        )[SignUpViewModel::class.java]

        backBtn.setOnClickListener { finish() }

        //handle signUp in
        signUp.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val fullName = fullNameEditText.text.toString()

            if (email.isNotEmpty()
                && password.isNotEmpty() && fullName.isNotEmpty()
            ) {
                signUp(email, password, fullName)
            } else {
                Toast.makeText(this, getString(R.string.please_fill_all_fields), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun signUp(email: String, password: String, fullName: String) {
        viewModel.signUp(
            this,
            email,
            password,
            fullName,
            loading
        )
        viewModel.signUp.observe(this, {
            if (it.status) {
                val i = Intent(this, SignInActivity::class.java)
                startActivity(i)
            } else {
                Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
            }
        })
    }
}