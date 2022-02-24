package com.gdsc.drsmart.ui.home.models

import java.io.Serializable

data class Result(
    val confidence: Double,
    val id: Int,
    val name: String,
    var isExpanded: Boolean
) : Serializable