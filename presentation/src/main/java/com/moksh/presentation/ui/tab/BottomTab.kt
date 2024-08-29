package com.moksh.presentation.ui.tab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.core.theme.booksIcon
import com.moksh.presentation.core.theme.homeIcon
import com.moksh.presentation.core.theme.passBookIcon
import com.moksh.presentation.ui.navigation.BottomNavigationGraph
import com.moksh.presentation.ui.navigation.TabRoutes
import com.moksh.presentation.ui.tab.components.WizzardBottomNavigationBar
import com.moksh.presentation.ui.tab.components.WizzardNavigationItem

@Composable
fun BottomTab(
    rootNavController: NavController,
) {
    val navController = rememberNavController()
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            BottomNavigationGraph(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter),
                navController = navController,
                rootNavController = rootNavController,
            )
            BottomNavigationBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                navController = navController
            )
        }
    }
}

@Composable
private fun BottomNavigationBar(modifier: Modifier = Modifier, navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestinationRoute = navBackStackEntry?.destination?.route
    val currentRoute = currentDestinationRoute?.substring(
        currentDestinationRoute.lastIndexOf(".") + 1,
        currentDestinationRoute.length
    )

    WizzardBottomNavigationBar(modifier = modifier) {
        WizzardNavigationItem(
            label = "HOME",
            isActive = currentRoute == TabRoutes.HomeTab.toString(),
            imageVector = homeIcon,
            onClick = {
                navController.navigate(TabRoutes.HomeTab) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
        )
        WizzardNavigationItem(
            label = "PASSBOOK",
            isActive = currentRoute == TabRoutes.PassBookTab.toString(),
            imageVector = passBookIcon,
            onClick = {
                navController.navigate(TabRoutes.PassBookTab) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
        )
        WizzardNavigationItem(
            label = "BOOKS",
            isActive = currentRoute == TabRoutes.BooksTab.toString(),
            imageVector = booksIcon,
            onClick = {
                navController.navigate(TabRoutes.BooksTab) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
        )
    }
}

@Composable
@Preview
private fun TabPagePreview() {
    WalletWizzardTheme {
        BottomTab(rootNavController = rememberNavController())
    }
}