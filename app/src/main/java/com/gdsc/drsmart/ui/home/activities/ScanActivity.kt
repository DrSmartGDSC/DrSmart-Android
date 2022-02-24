package com.gdsc.drsmart.ui.home.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

class ScanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_disease)

        val isLung = intent.getBooleanExtra("isLung", false)
        if (isLung) {
            stateImage.setImageResource(R.drawable.lungpic2)
            constraint.setBackgroundColor(resources.getColor(R.color.purple))
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
        viewModel = ViewModelProvider(
            this, ScanViewModelFactory(
                ScanRepository(
                    retrofitService
                )
            )
        )[ScanViewModel::class.java]
    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // use the returned uri
            val uriContent = result.uriContent
            val uriFilePath = result.getUriFilePath(this) // optional usage
            selectedImage = uriFilePath.toString()
            predict()
        } else {
            // an error occurred
            val exception = result.error
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        selectedImage = ""
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            val result = CropImage.getActivityResult(data)
//            if (resultCode == RESULT_OK) {
//                val resultUri: Uri = result.uri
//                selectedImage = resultUri.path!!
//                predict()
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                val error = result.error
//            }
//        }
//    }

    private fun predict() {
        val file = File(selectedImage)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val body = MultipartBody.Part.createFormData("img", file.name, requestFile)
        val type = RequestBody.create(MediaType.parse("multipart/form-data"), "0")

        viewModel.doPredict(
            this,
            AppReferences.getToken(this),
            body, type, load
        )

        viewModel.predictResponse.observe(this) {
            val i = Intent(this, ResultActivity::class.java)
            i.putExtra("response", it)
            startActivity(i)
        }
    }

    private fun uploadImage(): RequestBody? {
        return run {
            val file = File(selectedImage)
            RequestBody.create(MediaType.parse("*/*"), file)
        }
    }

    private fun createPart(`object`: Any): RequestBody? {
        return RequestBody.create(MultipartBody.FORM, `object`.toString())
    }

}