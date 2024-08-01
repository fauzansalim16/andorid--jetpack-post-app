package com.example.frontend_amdroid.navigation

// navigation/NavGraph.kt

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.frontend_amdroid.ui.add.AddScreen
import com.example.frontend_amdroid.ui.components.BottomNavItem
import androidx.compose.ui.Modifier
import com.example.frontend_amdroid.ui.home.HomeScreen
import com.example.frontend_amdroid.ui.settings.SettingsScreen

@Composable
fun NavGraph(navController: NavHostController, padding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = Modifier.padding(padding)
    ) {
        composable("home") { HomeScreen() }
        composable("add") { AddScreen() }
        composable("settings") { SettingsScreen() }
    }
}