package com.alican.mvvm_starter.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alican.mvvm_starter.ui.detail.MovieDetailScreen
import com.alican.mvvm_starter.ui.favorites.FavoritesScreen
import com.alican.mvvm_starter.ui.home.HomeScreen
import com.alican.mvvm_starter.ui.list.ListScreen
import com.alican.mvvm_starter.ui.theme.MoviesWithComposeTheme
import com.alican.mvvm_starter.util.NavigationModalItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ComposeActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MoviesWithComposeTheme {
                val navController = rememberNavController()

                val navigation: (String) -> Unit = { route ->
                    if (route == "-1") {
                        navController.popBackStack()
                    } else {
                        navController.navigate(route)
                    }
                }


                val items = listOf(
                    NavigationModalItem(
                        title = "Home",
                        selectedIcon = Icons.Filled.Home,
                        unSelectedIcon = Icons.Outlined.Home,
                        route = "home"

                    ),
                    NavigationModalItem(
                        title = "Favorites",
                        selectedIcon = Icons.Filled.Favorite,
                        unSelectedIcon = Icons.Outlined.FavoriteBorder,
                        route = "favorites"
                    ),
                )
                var selectedItemIndex by rememberSaveable {
                    mutableIntStateOf(0)
                }
                val scope = rememberCoroutineScope()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    ModalNavigationDrawer(drawerContent = {
                        ModalDrawerSheet {
                            items.forEachIndexed { index, item ->
                                NavigationDrawerItem(
                                    label = { Text(text = item.title) },
                                    selected = index == selectedItemIndex,
                                    onClick = {
                                        navigation(item.route)
                                        selectedItemIndex = index
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unSelectedIcon,
                                            contentDescription = "icon"
                                        )
                                    },
                                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding))

                            }
                        }
                    }, drawerState = drawerState) {
                        Scaffold(
                        ) { paddingValues ->
                            NavHost(
                                navController = navController,
                                startDestination = "home",
                                modifier = Modifier.padding(paddingValues)
                            ) {
                                composable("home") {
                                    HomeScreen(
                                        openList = {
                                            navigation(it)
                                        },
                                        openDetail = {
                                            navigation(it)
                                        },
                                        openFavorites = {
                                            navigation(it)
                                        },
                                        menuClicked =  {
                                            scope.launch {
                                                drawerState.open()
                                            }
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
                                        ListScreen(
                                            openDetail = {
                                                navigation(it)
                                            },
                                            openFavorites = {
                                                navigation(it)
                                            },
                                            popBackStack = {
                                                navigation(it)
                                            })
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
                                            },
                                            onFavoriteClick = {
                                                navigation(it)
                                            })
                                    }
                                }
                                composable(
                                    route = "favorites",
                                ) { entry ->
                                    FavoritesScreen(
                                        openDetail = {
                                            navigation(it)
                                        },
                                        popBackStack = {
                                            navigation(it)
                                        },
                                        menuClicked = {
                                            scope.launch {
                                                drawerState.open()
                                            }
                                        }
                                    )
                                }
                            }
                        }


                    }
                }
            }
        }
    }
}