package com.moksh.presentation.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
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
import com.moksh.presentation.ui.edit_profile.EditProfileScreen
import com.moksh.presentation.ui.tab.BottomTab

@Composable
fun WalletWizzardGraph() {
    val navController: NavHostController = rememberNavController()
    WalletWizzardTheme {
        NavHost(
            navController = navController, startDestination = HomeRoutes.HomeGraph,
            enterTransition = { slideInHorizontally(tween(700), initialOffsetX = { it }) },
            exitTransition = { slideOutHorizontally(tween(700), targetOffsetX = { it }) },
        ) {
            authGraph(navController)
            homeGraph(navController)
        }
    }
}

private fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation<AuthRoutes.AuthGraph>(
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
                    navController.navigate(TabRoutes.TabGraph)
                }
            )
        }
    }
}

private fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation<HomeRoutes.HomeGraph>(
        startDestination = TabRoutes.TabGraph,
    ) {
        composable<HomeRoutes.WalletWizzardScreen> {
//            WalletWizzardScreen()
        }
        composable<HomeRoutes.EditProfileScreen> {
            EditProfileScreen()
        }
        composable<TabRoutes.TabGraph> {
            BottomTab(
                onEditProfileClick = {
                    navController.navigate(HomeRoutes.EditProfileScreen)
                }
            )
        }
    }
}
