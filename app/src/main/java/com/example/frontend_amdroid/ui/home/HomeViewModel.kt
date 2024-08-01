package com.example.frontend_amdroid.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_amdroid.data.model.Post
import com.example.frontend_amdroid.data.repository.PostRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = PostRepository()
    // LiveData to Lifecycle-aware(monitor) data posts
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val fetchedPosts = repository.getPosts()
                _posts.value = fetchedPosts
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error fetching posts: ${e.message}"
                Log.e("HomeViewModel", "Error fetching posts", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}