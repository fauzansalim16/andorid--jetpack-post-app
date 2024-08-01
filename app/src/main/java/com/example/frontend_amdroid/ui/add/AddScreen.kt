package com.example.frontend_amdroid.ui.add

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter

@Composable
fun AddScreen(viewModel: AddViewModel = viewModel()) {
    // remember fun is to keep variable value
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val selectedImageUri by viewModel.selectedImageUri.collectAsState()
    val postCreationStatus by viewModel.postCreationStatus.collectAsState()
    val context = LocalContext.current

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            viewModel.setSelectedImage(uri)
        }
    )

    LaunchedEffect(postCreationStatus) {
        when (postCreationStatus) {
            is PostCreationStatus.Success -> {
                Toast.makeText(context, "Post created successfully!", Toast.LENGTH_SHORT).show()
                // Reset form fields
                title = ""
                description = ""
                viewModel.setSelectedImage(null)
                viewModel.resetPostCreationStatus()
            }
            is PostCreationStatus.Error -> {
                Toast.makeText(context, (postCreationStatus as PostCreationStatus.Error).message, Toast.LENGTH_LONG).show()
                viewModel.resetPostCreationStatus()
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Create New Post", style = MaterialTheme.typography.h5)

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 5
        )

        Button(
            onClick = { imagePicker.launch("image/*") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Add Image")
        }

        selectedImageUri?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Selected image",
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { viewModel.createPost(context, title, description) },
            modifier = Modifier.fillMaxWidth(),
            enabled = postCreationStatus !is PostCreationStatus.Loading
        ) {
            if (postCreationStatus is PostCreationStatus.Loading) {
                CircularProgressIndicator(color = Color.White)
            } else {
                Text("Create Post")
            }
        }
    }
}