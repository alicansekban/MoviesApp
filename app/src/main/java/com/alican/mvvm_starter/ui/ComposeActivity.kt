package com.alican.mvvm_starter.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alican.mvvm_starter.ui.detail.MovieDetailScreen
import com.alican.mvvm_starter.ui.home.HomeScreen
import com.alican.mvvm_starter.ui.list.ListScreen
import com.alican.mvvm_starter.ui.theme.MoviesWithComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComposeActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MoviesWithComposeTheme() {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") {
                        HomeScreen(
                            openList = {
                                navController.navigate("list/{type}".replace(oldValue = "{type}", newValue = it))
                            },
                            openDetail = {
                                navController.navigate("detail/{id}".replace(oldValue = "{id}", newValue = it.toString()))
                            }
                        )
                    }
                    composable(
                        route = "list/{type}",
                        arguments = listOf(
                            navArgument("type") {
                                type = NavType.StringType
                            }
                        )
                    ) { entry ->
                        val type = entry.arguments?.getString("type")
                        if (type != null) {
                            ListScreen(navController, type)
                        } else {
                            // Hata durumunda yapılacak işlem
                        }
                    }
                    composable(
                        route = "detail/{id}",
                        arguments = listOf(
                            navArgument("id") {
                                type = NavType.IntType
                            }
                        )
                    ) { entry ->
                        val id = entry.arguments?.getInt("id")
                        if (id != null) {
                            MovieDetailScreen(id, navController = navController)
                        } else {
                            // Hata durumunda yapılacak işlem
                        }
                    }
                }
            }
        }
    }
}