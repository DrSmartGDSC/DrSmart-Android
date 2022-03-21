package com.gdsc.drsmart.ui.home.repo

import com.gdsc.drsmart.tools.network.RetrofitService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ScanRepository(
    private val retrofitService: RetrofitService,
) {
    fun doPredict(auth: String, img: MultipartBody.Part, type: RequestBody) =
        retrofitService.doPredict(auth, img, type)

    fun getInfo(auth: String, id: Int, fieldId: Int) =
        retrofitService.getInfo(auth, id, fieldId)
}
