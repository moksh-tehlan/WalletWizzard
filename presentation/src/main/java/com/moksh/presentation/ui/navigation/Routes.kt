package com.moksh.presentation.ui.navigation

import kotlinx.serialization.Serializable


interface Routes

sealed interface Graphs : Routes {
    @Serializable
    data object AuthGraph : Graphs

    @Serializable
    data object HomeGraph : Graphs
}

sealed interface AuthRoutes : Routes {

    @Serializable
    data object PhoneLoginScreen : AuthRoutes

    @Serializable
    data class OtpVerificationScreen(val phoneNumber: String) : AuthRoutes
}

sealed interface HomeRoutes : Routes {

    @Serializable
    data object EditProfileScreen : HomeRoutes

    @Serializable
    data object WalletWizzardScreen : Routes
}

sealed interface TabRoutes : Routes {

    @Serializable
    data object HomeTab : TabRoutes

    @Serializable
    data object PassBookTab : TabRoutes

    @Serializable
    data object BooksTab : TabRoutes

    @Serializable
    data object ProfileTab : TabRoutes
}