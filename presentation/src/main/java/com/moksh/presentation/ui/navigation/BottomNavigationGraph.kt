package com.moksh.presentation.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moksh.presentation.ui.books_tab.BooksTab
import com.moksh.presentation.ui.home_tab.HomeTab
import com.moksh.presentation.ui.passbook_tab.PassbookTab
import com.moksh.presentation.ui.profile_tab.ProfileTab

@Composable
fun BottomNavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = TabRoutes.ProfileTab,
        enterTransition = { fadeIn(tween(500)) },
        exitTransition = { fadeOut(tween(500)) }
    ) {
        composable<TabRoutes.HomeTab> {
            HomeTab()
        }
        composable<TabRoutes.PassBookTab> {
            PassbookTab()
        }
        composable<TabRoutes.BooksTab> {
            BooksTab()
        }
        composable<TabRoutes.ProfileTab> {
            ProfileTab()
        }
    }
}