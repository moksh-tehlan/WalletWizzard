package com.moksh.presentation.core.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.moksh.presentation.R

private val DarkColorScheme = darkColorScheme(
    background = WizzardBlack,
    onBackground = WizzardWhite,
    onSecondaryContainer = WizzardWhite,
    primaryContainer = WizzardWhite,
    onPrimaryContainer = WizzardBlack,
    surface = WizzardGrey,
    surfaceVariant = WizzardLightGrey
)

@Composable
fun WalletWizzardTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.decorView.setBackgroundResource(R.color.wizzard_black)
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = WizzardTypography,
        content = content
    )
}