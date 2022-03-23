package com.gdsc.drsmart.ui.register.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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

var isDoc: Boolean = false
var field_id: Int = 0
var is_doctor: Int = 0// is doctor == 1 , 0 for user

class SignUpActivity : AppCompatActivity() {
    lateinit var viewModel: SignUpViewModel
    private val retrofitService = RetrofitService.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        initView()
        if (isDoc) {
            is_doctor = 1;
            helloMessageTxt.text = getString(R.string.hello_dr)
            initSpinner()
        } else {
            helloMessageTxt.text = getString(R.string.create_account)
        }
    }

    private fun initView() {
        isDoc = intent.getBooleanExtra("isDoc", false)
        viewModel = ViewModelProvider(
            this, SignUpViewModelFactory(
                RegisterRepository(
                    retrofitService
                )
            )
        )[SignUpViewModel::class.java]

        backBtn.setOnClickListener { finish() }
        helloMessageTxt.text = getString(R.string.hello_dr)

        //handle signUp in
        signUp.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val fullName = fullNameEditText.text.toString()

            if (!isDoc) field_id = -1 // field id will equal -1 for user
            if (email.isNotEmpty()
                && password.isNotEmpty() && fullName.isNotEmpty() && field_id != 0
            ) {
                signUp(email, password, fullName)
            } else {
                Toast.makeText(this, getString(R.string.please_fill_all_fields), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun initSpinner() {
        fieldsSpinner.visibility = View.VISIBLE
        viewModel.getFields(this, loading)
        val fields: ArrayList<String> = ArrayList()
        fields.add("Select your field")
        fieldsSpinner.setSelection(0)
        var adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item, fields
        )
        fieldsSpinner.adapter = adapter
        viewModel.fields.observe(this, {
            if (it.status) {
                for (i in it.data.fields) {
                    fields.add(i.name)
                }
                adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_dropdown_item, fields
                )
                fieldsSpinner.adapter = adapter
            }
        })
        fieldsSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                if (position != 0) {
                    field_id = position
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

    }


    private fun signUp(email: String, password: String, fullName: String) {
        viewModel.signUp(
            this,
            email,
            password,
            fullName,
            is_doctor,
            field_id,
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