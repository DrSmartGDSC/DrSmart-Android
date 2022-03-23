package com.gdsc.drsmart.ui.home.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.gdsc.drsmart.R
import com.gdsc.drsmart.tools.network.RetrofitService
import com.gdsc.drsmart.tools.storage.AppReferences
import com.gdsc.drsmart.ui.home.repo.ScanRepository
import com.gdsc.drsmart.ui.home.viewModels.ScanViewModel
import com.gdsc.drsmart.ui.home.viewModels.factory.ScanViewModelFactory
import kotlinx.android.synthetic.main.activity_scan_disease.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


lateinit var selectedImage: String
lateinit var viewModel: ScanViewModel
private val retrofitService = RetrofitService.getInstance()
var isLung: Boolean = false

class ScanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_disease)

        initView()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this, ScanViewModelFactory(
                ScanRepository(
                    retrofitService
                )
            )
        )[ScanViewModel::class.java]
    }

    private fun initView() {
        // if it's lung i will type = 1
        isLung = intent.getBooleanExtra("isLung", false)

        if (isLung) {
            stateImage.setImageResource(R.drawable.lungpic2)
            constraint.setBackgroundColor(ContextCompat.getColor(this, R.color.purple))
            scanTitle.text = getString(R.string.show_me_your_x_ray)
        }
        close.setOnClickListener {
            finish()
        }
        uploadBtn.setOnClickListener {
            cropImage.launch(
                options {
                    setGuidelines(CropImageView.Guidelines.ON)
                }
            )
        }
    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            val uriFilePath = result.getUriFilePath(this)
            selectedImage = uriFilePath.toString()
            predict()
        } else {
            val exception = result.error
            Log.d("cropEx", exception!!.message.toString())
        }
    }

    private fun predict() {
        val file = File(selectedImage)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val body = MultipartBody.Part.createFormData("img", file.name, requestFile)
        var type = RequestBody.create(MediaType.parse("multipart/form-data"), "0")
        if (isLung) {
            type = RequestBody.create(MediaType.parse("multipart/form-data"), "1")
        }

        viewModel.doPredict(
            this,
            AppReferences.getToken(this),
            body, type, load
        )

        viewModel.predictResponse.observe(this) {
            val i = Intent(this, ResultActivity::class.java)
            i.putExtra("response", it)
            i.putExtra("type", isLung)
            startActivity(i)
            finish()
        }
    }
}