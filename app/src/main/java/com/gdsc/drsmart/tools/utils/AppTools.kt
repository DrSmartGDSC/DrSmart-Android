package com.gdsc.drsmart.tools.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

object AppTools {
    var questionsLimit = 10
    fun loadImage(ctx: Context, view: ImageView, url: String) {
        Glide
            .with(ctx)
            .load(url)
            .into(view)
    }
}