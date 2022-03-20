package com.gdsc.drsmart.ui.doctor.models.comment

data class Comment(
    val comment_id: Int,
    val img: String,
    val text: String,
    val user_id: Int,
    val user_name: String
)