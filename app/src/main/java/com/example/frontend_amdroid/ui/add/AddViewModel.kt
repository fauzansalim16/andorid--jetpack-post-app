package com.example.frontend_amdroid.ui.add

// ui/add/AddViewModel.kt

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import android.content.Context
import okhttp3.RequestBody.Companion.toRequestBody
import com.example.frontend_amdroid.data.api.RetrofitInstance
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

class AddViewModel : ViewModel() {
    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    val selectedImageUri: StateFlow<Uri?> = _selectedImageUri

    fun setSelectedImage(uri: Uri?) {
        _selectedImageUri.value = uri
    }

    fun createPost(context: Context, title: String, description: String) {
        viewModelScope.launch {
            try {
                val imageUri = _selectedImageUri.value ?: throw IllegalStateException("Image not selected")

                val titleBody = title.toRequestBody("text/plain".toMediaType())
                val descriptionBody = description.toRequestBody("text/plain".toMediaType())

                val imageFile = uriToFile(context, imageUri)
                val imagePart = MultipartBody.Part.createFormData(
                    "image",
                    imageFile.name,
                    imageFile.asRequestBody("image/*".toMediaType())
                )

                val response = RetrofitInstance.api.createPost(titleBody, descriptionBody, imagePart)

                if (response.isSuccessful) {
                    // Handle success
                    println("Post created successfully")
                } else {
                    // Handle error
                    println("Error creating post: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                // Handle exception
                println("Exception when creating post: ${e.message}")
            }
        }
    }

    private fun uriToFile(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "temp_image")
        val outputStream = FileOutputStream(file)
        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return file
    }
}