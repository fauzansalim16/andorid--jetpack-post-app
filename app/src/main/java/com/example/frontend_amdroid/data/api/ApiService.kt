package com.example.frontend_amdroid.data.api

import com.example.frontend_amdroid.data.model.Post
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}