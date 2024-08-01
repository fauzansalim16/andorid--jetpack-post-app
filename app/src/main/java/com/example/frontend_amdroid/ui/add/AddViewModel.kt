package com.example.frontend_amdroid.ui.add

// ui/add/AddViewModel.kt

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import android.content.Context
import android.webkit.MimeTypeMap
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

    private val _postCreationStatus = MutableStateFlow<PostCreationStatus>(PostCreationStatus.Initial)
    val postCreationStatus: StateFlow<PostCreationStatus> = _postCreationStatus

    fun setSelectedImage(uri: Uri?) {
        _selectedImageUri.value = uri
    }

    fun createPost(context: Context, title: String, description: String) {
        viewModelScope.launch {
            try {
                _postCreationStatus.value = PostCreationStatus.Loading
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
                    _postCreationStatus.value = PostCreationStatus.Success
                } else {
                    _postCreationStatus.value = PostCreationStatus.Error("Error creating post: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _postCreationStatus.value = PostCreationStatus.Error("Exception when creating post: ${e.message}")
            }
        }

    }
    fun resetPostCreationStatus() {
        _postCreationStatus.value = PostCreationStatus.Initial
    }
    private fun uriToFile(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val fileExtension = context.contentResolver.getType(uri)?.let {
            MimeTypeMap.getSingleton().getExtensionFromMimeType(it)
        } ?: "jpg"  // default to jpg if we can't determine the type
        val file = File(context.cacheDir, "temp_image.$fileExtension")
        val outputStream = FileOutputStream(file)
        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return file
    }
}

sealed class PostCreationStatus {
    object Initial : PostCreationStatus()
    object Loading : PostCreationStatus()
    object Success : PostCreationStatus()
    data class Error(val message: String) : PostCreationStatus()
}