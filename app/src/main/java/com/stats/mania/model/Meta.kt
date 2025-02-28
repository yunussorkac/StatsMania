package com.stats.mania.model

import com.google.gson.annotations.SerializedName

data class Meta(
    val page: Int,
    val pages: Int,
    @SerializedName("per_page") val perPage: String,
    val total: Int
)