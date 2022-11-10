package com.thoughtctl.model

data class SearchItem(
    val id: String?,
    val title: String?,
    val datetime: Long?,
    val link: String?,
    val images: Array<Images>?
)