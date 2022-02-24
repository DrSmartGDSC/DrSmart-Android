package com.gdsc.drsmart.ui.home.viewModels

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gdsc.drsmart.ui.home.models.PredictResponse
import com.gdsc.drsmart.ui.home.repo.ScanRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScanViewModel(private val repository: ScanRepository) : ViewModel() {
    var predictResponse: MutableLiveData<PredictResponse> = MutableLiveData()

    fun doPredict(
        ctx: Context,
        auth: String,
        img: MultipartBody.Part,
        type: RequestBody,
        view: View
    ) {
        view.visibility = View.VISIBLE
        val response = repository.doPredict(auth, img, type)
        response.enqueue(object : Callback<PredictResponse> {
            override fun onResponse(
                call: Call<PredictResponse>,
                response: Response<PredictResponse>
            ) {
                view.visibility = View.GONE
                predictResponse.value = response.body()
//                Log.e("amr", response.body().toString())
            }

            override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                view.visibility = View.GONE
//                Log.e("amr", t.message + "  ")
                Toast.makeText(ctx, "Error", Toast.LENGTH_LONG).show()
            }
        })
    }
}