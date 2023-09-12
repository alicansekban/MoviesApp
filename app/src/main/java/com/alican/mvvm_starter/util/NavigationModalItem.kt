package com.alican.mvvm_starter.util

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationModalItem(
    val title : String,
    val selectedIcon  : ImageVector,
    val unSelectedIcon : ImageVector,
    val route : String,
)
