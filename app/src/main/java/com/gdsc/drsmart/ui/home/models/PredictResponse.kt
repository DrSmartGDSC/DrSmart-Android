package com.gdsc.drsmart.ui.home.models

import java.io.Serializable

data class PredictResponse(
    val `data`: Data,
    val status: Boolean
) : Serializable