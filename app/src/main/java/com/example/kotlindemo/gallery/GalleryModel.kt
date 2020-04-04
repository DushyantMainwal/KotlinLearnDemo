package com.example.kotlindemo.gallery

data class GalleryModel(val bucketPath: String, val bucketTitle: String, val bucketID: Int, var imageCount: Int = 0)