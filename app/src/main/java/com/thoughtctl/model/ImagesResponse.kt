package com.thoughtctl.model

data class ImagesResponse(
    val success: String,
    val status: Int,
    val data: GaleryArray?
)