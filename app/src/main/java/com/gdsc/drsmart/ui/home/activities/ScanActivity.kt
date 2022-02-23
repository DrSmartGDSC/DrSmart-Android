package com.gdsc.drsmart.ui.home.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gdsc.drsmart.R
import com.gdsc.drsmart.tools.network.RetrofitService
import com.gdsc.drsmart.tools.storage.AppReferences
import com.gdsc.drsmart.ui.home.repo.ScanRepository
import com.gdsc.drsmart.ui.home.viewModels.ScanViewModel
import com.gdsc.drsmart.ui.home.viewModels.factory.ScanViewModelFactory
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
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
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
        }
        viewModel = ViewModelProvider(
            this, ScanViewModelFactory(
                ScanRepository(
                    retrofitService
                )
            )
        )[ScanViewModel::class.java]
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        selectedImage = ""
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri: Uri = result.uri
                selectedImage = resultUri.path!!
                predict()
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }

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

//        viewModel.predictResponse.observe(this) {
////            if (it.status) {
//            Toast.makeText(this, it.data.result[0].name + "", Toast.LENGTH_SHORT).show()
////            }
//        }
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