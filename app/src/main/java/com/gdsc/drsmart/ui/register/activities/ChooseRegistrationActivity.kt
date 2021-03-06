package com.gdsc.drsmart.ui.register.activities

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.gdsc.drsmart.MainActivity
import com.gdsc.drsmart.R
import com.gdsc.drsmart.tools.storage.AppReferences
import com.gdsc.drsmart.ui.doctor.activities.DoctorHomePage
import kotlinx.android.synthetic.main.activity_choose_registeration.*

class ChooseRegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkLoginState()
        setContentView(R.layout.activity_choose_registeration)
        initView()
    }

    private fun checkLoginState() {
        val loginState = intent.getBooleanExtra("login_state", false)
        if (loginState) {
            AppReferences.setLoginState(this, false)
            AppReferences.setDocLoginState(this, false)
            reopen()
        }
        if (AppReferences.getDocLoginState(this)) {
            val i = Intent(this, DoctorHomePage::class.java)
            startActivity(i)
            finish()
        }
        if (AppReferences.getLoginState(this)) {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun reopen() {
        val intent = Intent(this, ChooseRegistrationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        this@ChooseRegistrationActivity.finish()
    }

    override fun onKeyDown(keycode: Int, event: KeyEvent?): Boolean {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true)
        }
        return super.onKeyDown(keycode, event)
    }

    private fun initView() {
        doctorBtn.setOnClickListener {
            val i = Intent(this, RegistrationHomeActivity::class.java)
            i.putExtra("isDoctor", true)
            startActivity(i)
        }
        patientBtn.setOnClickListener {
            val i = Intent(this, RegistrationHomeActivity::class.java)
            i.putExtra("isDoctor", false)
            startActivity(i)
        }
    }
}