package com.ahmed.domain.model

data class File(
    val id: String,
    val type: String,
    val url: String,
    val name: String,
    var progressLoading:Int=0,
    var isLoading: Boolean = false
)
