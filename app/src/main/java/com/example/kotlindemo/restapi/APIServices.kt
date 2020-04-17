package com.example.kotlindemo.restapi

import com.example.kotlindemo.restapi.models.ListResourceModel
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface APIServices {

    @FormUrlEncoded
    @POST("/api/login")
    fun loginUser(@Field("email") email: String, @Field("password") password: String): Call<JsonObject>

    @GET("/api/unknown")
    fun getResources(@Query("page") page: Int) : Call<ListResourceModel>

    companion object {
        fun create(): APIServices {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://reqres.in")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            return retrofit.create(APIServices::class.java)
        }
    }

}