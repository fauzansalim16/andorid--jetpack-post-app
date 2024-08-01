package com.example.frontend_amdroid.ui.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

//sealed class used to limit hierarchy of the class
sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    data object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    data object Add : BottomNavItem("add", Icons.Default.Add, "Add")
    data object Settings : BottomNavItem("settings", Icons.Default.Settings, "Settings")
}

@Composable
fun BottomNav(currentRoute: String?, onNavItemClick: (BottomNavItem) -> Unit) {
    val items = listOf(BottomNavItem.Home, BottomNavItem.Add, BottomNavItem.Settings)

    BottomNavigation {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = { onNavItemClick(item) }
            )
        }
    }
}
