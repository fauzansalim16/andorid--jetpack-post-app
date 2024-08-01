package com.example.frontend_amdroid.data.api

import com.example.frontend_amdroid.data.model.Post
import retrofit2.http.GET
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import okhttp3.MultipartBody


interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>

    @Multipart
    @POST("posts")
    suspend fun createPost(
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<Unit>
}