package com.moksh.presentation.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.ui.auth.otp.OtpVerificationScreen
import com.moksh.presentation.ui.auth.phone.PhoneLoginScreen
import com.moksh.presentation.ui.books_tab.BooksTab
import com.moksh.presentation.ui.home_tab.HomeTab
import com.moksh.presentation.ui.passbook_tab.PassbookTab
import com.moksh.presentation.ui.passbook_tab.passbook_entry.AddNewPassbookEntry
import com.moksh.presentation.ui.profile.ProfileScreen
import com.moksh.presentation.ui.savings.new_pocket.AddNewSavingsPocketScreen
import com.moksh.presentation.ui.tab.BottomTab

@Composable
fun WalletWizzardGraph() {
    val navController: NavHostController = rememberNavController()
    WalletWizzardTheme {
        NavHost(
            navController = navController, startDestination = Graphs.HomeGraph,
            enterTransition = { slideInHorizontally(tween(700), initialOffsetX = { it }) },
            exitTransition = { slideOutHorizontally(tween(700), targetOffsetX = { it }) },
        ) {
            authGraph(navController)
            homeGraph(navController)
        }
    }
}

private fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation<Graphs.AuthGraph>(
        startDestination = AuthRoutes.PhoneLoginScreen,
    ) {
        composable<AuthRoutes.PhoneLoginScreen> {
            PhoneLoginScreen(
                onOtpSent = { phoneNumber ->
                    navController.navigate(AuthRoutes.OtpVerificationScreen(phoneNumber = phoneNumber))
                }
            )
        }
        composable<AuthRoutes.OtpVerificationScreen> {
            OtpVerificationScreen(
                onOtpVerified = {
                    navController.navigate(Graphs.HomeGraph)
                }
            )
        }
    }
}

private fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation<Graphs.HomeGraph>(
        startDestination = HomeRoutes.WalletWizzardScreen,
    ) {
        composable<HomeRoutes.EditProfileScreen> {
            ProfileScreen()
        }
        composable<HomeRoutes.AddNewSavingsPocketScreen> {
            AddNewSavingsPocketScreen()
        }
        composable<HomeRoutes.WalletWizzardScreen> {
            BottomTab(
                rootNavController = navController,
            )
        }
        composable<HomeRoutes.AddNewPassbookEntry> {
            AddNewPassbookEntry(
                onTransactionSave = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun BottomNavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    rootNavController: NavController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = TabRoutes.HomeTab,
        enterTransition = { fadeIn(tween(500)) },
        exitTransition = { fadeOut(tween(500)) }
    ) {
        composable<TabRoutes.HomeTab> {
            HomeTab(
                onAddNewSavingsPocket = {
                    rootNavController.navigate(HomeRoutes.AddNewSavingsPocketScreen)
                },
                onProfileClick = {
                    rootNavController.navigate(HomeRoutes.EditProfileScreen)
                }
            )
        }
        composable<TabRoutes.PassBookTab> {
            PassbookTab(
                onNewEntry = { entryType ->
                    rootNavController.navigate(HomeRoutes.AddNewPassbookEntry(entryType = entryType.name))
                }
            )
        }
        composable<TabRoutes.BooksTab> {
            BooksTab()
        }
//        composable<TabRoutes.ProfileTab> {
//            ProfileTab(onEditProfileClick = {
//                rootNavController.navigate(HomeRoutes.EditProfileScreen)
//            })
//        }
    }
}
