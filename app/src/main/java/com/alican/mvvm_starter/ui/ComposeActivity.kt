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
                                navController.navigate("list/{type}")
                            },
                            openDetail = {
                                navController.navigate("detail/{id}")
                            },
                            navController
                        )
                    }
                    composable(route =  "list/{type}",
                        arguments = listOf(
                            navArgument("type"){
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val type = it.arguments?.getString("type")
                        ListScreen(navController,type)
                    }
                    composable(
                        route = "detail/{id}",
                        arguments = listOf(
                            navArgument("id") {
                                type = NavType.IntType
                            }
                        )
                    ) {
                        val id = it.arguments?.getInt("id")
                        if (id != null) {
                            MovieDetailScreen(id)
                        }
                    }
                }
            }
        }
    }
}