package com.gdsc.drsmart.ui.home.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gdsc.drsmart.R
import kotlinx.android.synthetic.main.activity_lung.*
import kotlinx.android.synthetic.main.activity_sign_in.backBtn

class SkinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skin)
        initViews()
    }

    private fun initViews() {
        backBtn.setOnClickListener {
            finish()
        }
        findOutBtn.setOnClickListener {
            val i = Intent(this, ScanActivity::class.java)
            startActivity(i)
        }
    }
}