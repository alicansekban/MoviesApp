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
class ComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MoviesWithComposeTheme() {
                val navController = rememberNavController()

                val navigation: (String) -> Unit = { route ->
                    if (route == "-1") {
                        navController.popBackStack()
                    } else {
                        navController.navigate(route)
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") {
                        HomeScreen(
                            openList = {
                                navigation(it)
                            },
                            openDetail = {
                               navigation(it)
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
                            ListScreen(type = type,
                                openDetail = {
                                    navigation(it)
                                },
                                popBackStack = {
                                    navigation(it)
                                })
                        } else {
                            // Hata durumunda yapılacak işlem
                        }
                    }
                    composable(
                        route = "detail/{id}",
                        arguments = listOf(
                            navArgument("id") {
                                type = NavType.StringType
                            }
                        )
                    ) { entry ->
                        val id = entry.arguments?.getString("id")
                        if (id != null) {
                            MovieDetailScreen(
                                popBackStack = {
                                    navigation(it)
                                })
                        } else {
                            // Hata durumunda yapılacak işlem
                        }
                    }
                }
            }
        }
    }
}