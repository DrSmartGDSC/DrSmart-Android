package com.gdsc.drsmart.ui.register.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gdsc.drsmart.R
import kotlinx.android.synthetic.main.activity_registration_home.*

class RegistrationHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_home)

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