package com.gdsc.drsmart.ui.doctor.models.posts

import java.io.Serializable

data class PostsResponse(
    val `data`: Data,
    val status: Boolean
):Serializable