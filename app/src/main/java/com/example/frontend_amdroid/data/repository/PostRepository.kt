package com.example.frontend_amdroid.data.repository

import com.example.frontend_amdroid.data.api.RetrofitClient
import com.example.frontend_amdroid.data.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostRepository {
    private val apiService = RetrofitClient.instance

    suspend fun getPosts(): List<Post> {
        return withContext(Dispatchers.IO) {
            apiService.getPosts()
        }
    }
}