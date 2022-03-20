package com.gdsc.drsmart.tools.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

object Base64Utils {
    fun decodeToBitmap(base: String): Bitmap {
        val decodedString: ByteArray =
            Base64.decode(base, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size);
    }
}