package com.example.kotlindemo.restapi.models

import com.google.gson.annotations.SerializedName

data class Ad(
    @SerializedName("company")
    val company: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("url")
    val url: String
)