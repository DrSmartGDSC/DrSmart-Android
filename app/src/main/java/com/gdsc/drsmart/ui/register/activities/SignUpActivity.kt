package com.gdsc.drsmart.ui.register.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gdsc.drsmart.R
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        backBtn.setOnClickListener {
            finish();
        }
    }
}