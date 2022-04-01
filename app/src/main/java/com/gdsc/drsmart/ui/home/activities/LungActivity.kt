package com.gdsc.drsmart.ui.home.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gdsc.drsmart.R
import kotlinx.android.synthetic.main.activity_lung.*
import kotlinx.android.synthetic.main.activity_sign_in.backBtn

class LungActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lung)
        initView()
    }

    private fun initView() {
        backBtn.setOnClickListener {
            finish()
        }
        findOutBtn.setOnClickListener {
            val i = Intent(this, ScanActivity::class.java)
            i.putExtra("isLung", true)
            startActivity(i)
        }
    }
}