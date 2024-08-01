package com.example.frontend_amdroid.ui.home

// ui/components/PostItem.kt

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.frontend_amdroid.data.model.Post

@Composable
fun PostItem(post: Post) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = post.Title, style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
                model = post.Image_url,
                contentDescription = "Post image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = post.Description, style = MaterialTheme.typography.body1)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Posted at: ${post.Posted_at}", style = MaterialTheme.typography.caption)
        }
    }
}