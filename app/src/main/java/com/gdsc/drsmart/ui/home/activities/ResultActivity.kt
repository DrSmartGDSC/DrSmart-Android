package com.gdsc.drsmart.ui.home.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.gdsc.drsmart.MainActivity
import com.gdsc.drsmart.R
import com.gdsc.drsmart.tools.storage.AppReferences
import com.gdsc.drsmart.ui.home.adapter.ResultAdapter
import com.gdsc.drsmart.ui.home.models.PredictResponse
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.activity_result.recycleView
import kotlinx.android.synthetic.main.ask_question_dialog.*
import kotlinx.android.synthetic.main.fragment_questions.*
import kotlinx.android.synthetic.main.more_info_dialog.*


private lateinit var dialog: Dialog

class ResultActivity : AppCompatActivity() {
    lateinit var resultsAdapter: ResultAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        initViews()
        askDoctor()
    }

    private fun initViews() {
        val response = intent.getSerializableExtra("response") as PredictResponse
        val type = intent.getIntExtra("type", 0)
        (recycleView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recycleView.layoutManager = LinearLayoutManager(this)
        resultsAdapter =
            ResultAdapter(this, response, object : ResultAdapter.onMoreInfoClickListner {
                override fun onClick(id: Int) {
                    viewModel.getInfo(
                        this@ResultActivity,
                        AppReferences.getToken(this@ResultActivity),
                        id, type
                    )

                }
            })

        recycleView.adapter = resultsAdapter
        viewModel.infoResponse.observe(this) {
            if (it.status) {
                showBottomSheetDialog(this, it.data.result)
            }
        }
        close.setOnClickListener {
            finish()
        }

    }

    private fun askDoctor() {
        findDocBtn.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            i.putExtra("isAsk", true)
            startActivity(i)
        }
    }

    private fun showBottomSheetDialog(context: Context, txt: String) {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.more_info_dialog)
        bottomSheetDialog.desc.text = txt

        bottomSheetDialog.show()
    }


    private fun showDialog(context: Context, txt: String) {
        dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        val window: Window = dialog.window!!
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.setContentView(R.layout.more_info_dialog)
        dialog.desc.text = txt
        dialog.show()
    }

}