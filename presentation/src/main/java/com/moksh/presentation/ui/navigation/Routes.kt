package com.moksh.presentation.ui.navigation

import kotlinx.serialization.Serializable


interface Routes

sealed interface AuthRoutes : Routes {
    @Serializable
    data object AuthGraph : AuthRoutes

    @Serializable
    data object PhoneLoginScreen : AuthRoutes

    @Serializable
    data class OtpVerificationScreen(val phoneNumber: String) : AuthRoutes
}

sealed interface HomeRoutes : Routes {
    @Serializable
    data object HomeGraph : HomeRoutes

    @Serializable
    data object WalletWizzardScreen : Routes
}

sealed interface TabRoutes : Routes {

    @Serializable
    data object TabGraph : TabRoutes

    @Serializable
    data object HomeTab : TabRoutes

    @Serializable
    data object PassBookTab : TabRoutes

    @Serializable
    data object BooksTab : TabRoutes

    @Serializable
    data object ProfileTab : TabRoutes
}