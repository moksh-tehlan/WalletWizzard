package com.moksh.presentation.ui.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.moksh.domain.usecases.user.GetUser
import com.moksh.domain.util.Result
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.core.utils.asUiText
import com.moksh.presentation.ui.auth.otp.OtpVerificationScreen
import com.moksh.presentation.ui.auth.phone.PhoneLoginScreen
import com.moksh.presentation.ui.books_tab.BooksTab
import com.moksh.presentation.ui.category.CategoryScreen
import com.moksh.presentation.ui.home_tab.HomeTab
import com.moksh.presentation.ui.passbook_entry.AddNewPassbookEntry
import com.moksh.presentation.ui.passbook_tab.PassbookTab
import com.moksh.presentation.ui.payment_mode.PaymentModeScreen
import com.moksh.presentation.ui.profile.ProfileScreen
import com.moksh.presentation.ui.savings.new_pocket.AddNewSavingsPocketScreen
import com.moksh.presentation.ui.tab.BottomTab
import timber.log.Timber

@Composable
fun WalletWizzardGraph(getUser: GetUser) {

    var startDestination by remember { mutableStateOf<Graphs?>(null) }
    LaunchedEffect(true) {
        val result = getUser.invoke()
        startDestination = when (result) {
            is Result.Success -> {
                Timber.d("User: ${result.data}")
                Graphs.HomeGraph
            }

            is Result.Error -> {
                Timber.d("Error: ${result.error.asUiText()}")
                Graphs.AuthGraph
            }
        }
    }
    val navController: NavHostController = rememberNavController()
    WalletWizzardTheme {
        if (startDestination != null) {
            NavHost(
                navController = navController, startDestination = startDestination!!,
                enterTransition = {
                    slideInHorizontally(
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = FastOutSlowInEasing
                        ),
                        initialOffsetX = { fullWidth -> fullWidth }
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = FastOutSlowInEasing
                        ),
                        targetOffsetX = { fullWidth -> -fullWidth }
                    ) + fadeOut(animationSpec = tween(500))
                },
                popEnterTransition = {
                    slideInHorizontally(
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = FastOutSlowInEasing
                        ),
                        initialOffsetX = { fullWidth -> -fullWidth }
                    ) + fadeIn(animationSpec = tween(500))
                },
                popExitTransition = {
                    slideOutHorizontally(
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = FastOutSlowInEasing
                        ),
                        targetOffsetX = { fullWidth -> fullWidth }
                    )
                }
            ) {
                authGraph(navController)
                homeGraph(navController)
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

private fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation<Graphs.AuthGraph>(
        startDestination = AuthRoutes.PhoneLoginScreen,
    ) {
        composable<AuthRoutes.PhoneLoginScreen> {
            PhoneLoginScreen(
                onOtpSent = { phoneNumber, verificationId ->
                    navController.navigate(
                        AuthRoutes.OtpVerificationScreen(
                            phoneNumber = phoneNumber,
                            verificationId = verificationId
                        )
                    )
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
            AddNewSavingsPocketScreen(
                onCreatingSuccess = {
                    navController.popBackStack()
                }
            )
        }
        composable<HomeRoutes.WalletWizzardScreen> {
            BottomTab(
                rootNavController = navController,
            )
        }
        composable<HomeRoutes.AddNewPassbookEntry> { backstackEntry ->
            AddNewPassbookEntry(
                onTransactionSave = {
                    navController.popBackStack()
                },
                onSelectCategory = { transactionType, category ->
                    navController.navigate(
                        HomeRoutes.CategoryScreen(
                            transactionType = transactionType.name,
                            categoryId = category
                        )
                    )
                },
                onPaymentModeChange = { paymentModeId ->
                    navController.navigate(
                        HomeRoutes.PaymentModeScreen(
                            paymentModeId = paymentModeId
                        )
                    )
                },
                selectedCategoryId = backstackEntry.savedStateHandle.get<String>("categoryId"),
                selectedPaymentModeId = backstackEntry.savedStateHandle.get<String>("paymentModeId"),
            )
        }
        composable<HomeRoutes.CategoryScreen> {
            CategoryScreen(
                onBackPress = {
                    navController.popBackStack()
                },
                onCategorySaved = { category ->
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "categoryId",
                        category?.id
                    )
                    navController.popBackStack()
                }
            )
        }
        composable<HomeRoutes.PaymentModeScreen> {
            PaymentModeScreen(
                onBackPress = {
                    navController.popBackStack()
                },
                onPaymentModeSaved = { paymentMode ->
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "paymentModeId",
                        paymentMode?.id
                    )
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
                onNewEntry = { transactionType ->
                    rootNavController.navigate(HomeRoutes.AddNewPassbookEntry(entryType = transactionType.name))
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
