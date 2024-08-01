package com.example.frontend_amdroid.ui.add

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AddScreen(viewModel: AddViewModel = viewModel()) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

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
            onClick = { /* TODO: Implement image picker */ },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Add Image")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { viewModel.createPost(title, description) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Post")
        }
    }
}