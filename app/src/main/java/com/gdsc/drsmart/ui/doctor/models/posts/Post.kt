package com.gdsc.drsmart.ui.doctor.models.posts

import java.io.Serializable

data class Post(
    val answered: Boolean,
    val desc: String,
    val `field`: String,
    val img: String,
    val post_id: Int,
    val title: String,
    val user_name: String,
    val user_id: String

) : Serializable