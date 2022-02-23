package com.gdsc.drsmart.ui.register.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gdsc.drsmart.MainActivity
import com.gdsc.drsmart.R
import com.gdsc.drsmart.tools.storage.AppReferences
import kotlinx.android.synthetic.main.activity_registration_home.*

class RegistrationHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_home)

        if (AppReferences.getLoginState(this)) {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i);
        }
        signIn.setOnClickListener {
            val i = Intent(this, SignInActivity::class.java)
            startActivity(i);
        }
        signUp.setOnClickListener {
            val i = Intent(this, SignUpActivity::class.java)
            startActivity(i);
        }

    }
}