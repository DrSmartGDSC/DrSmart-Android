package com.gdsc.drsmart.ui.register.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.gdsc.drsmart.R
import kotlinx.android.synthetic.main.activity_registration_home.*

class RegistrationHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_home)
        initViews()
    }

    private fun initViews() {
        val isDoc = intent.getBooleanExtra("isDoctor", false)
        backBtn.setOnClickListener { finish() }
        if (isDoc) {
            imageView3.setImageResource(R.drawable.ic_plus)
            imageView3.scaleType = ImageView.ScaleType.CENTER
            docTxt.visibility = View.VISIBLE
        }

        signIn.setOnClickListener {
            val i = Intent(this, SignInActivity::class.java)
            i.putExtra("isDoc", isDoc)
            startActivity(i)
        }
        signUp.setOnClickListener {
            val i = Intent(this, SignUpActivity::class.java)
            i.putExtra("isDoc", isDoc)
            startActivity(i)
        }
    }

}