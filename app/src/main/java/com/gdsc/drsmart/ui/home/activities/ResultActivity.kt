package com.gdsc.drsmart.ui.home.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.gdsc.drsmart.R
import com.gdsc.drsmart.ui.home.adapter.ResultAdapter
import com.gdsc.drsmart.ui.home.models.PredictResponse
import kotlinx.android.synthetic.main.activity_result.*


class ResultActivity : AppCompatActivity() {
    lateinit var resultsAdapter: ResultAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val response = intent.getSerializableExtra("response") as PredictResponse
        (recycleView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recycleView.layoutManager = LinearLayoutManager(this)
        resultsAdapter =
            ResultAdapter(this, response)
        recycleView.adapter = resultsAdapter
    }

}