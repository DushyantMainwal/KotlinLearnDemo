package com.example.kotlindemo.restapi.models

import com.google.gson.annotations.SerializedName

data class PantonData(
    @SerializedName("color")
    val color: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("pantone_value")
    val pantoneValue: String,
    @SerializedName("year")
    val year: Int
)