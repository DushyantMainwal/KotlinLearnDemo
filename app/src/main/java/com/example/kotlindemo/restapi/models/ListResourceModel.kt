package com.example.kotlindemo.restapi.models

import com.google.gson.annotations.SerializedName

data class ListResourceModel(
    @SerializedName("id")
    val ad: Ad,
    @SerializedName("data")
    val data: List<PantonData>,
    @SerializedName("page")
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)