package com.moksh.presentation.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.moksh.presentation.R

val MartianMono = FontFamily(
    Font(
        resId = R.font.martianmono_semiexpanded_bold,
        weight = FontWeight.Bold
    ),
    Font(
        resId = R.font.martianmono_semiexpanded_extrabold,
        weight = FontWeight.ExtraBold
    ),
    Font(
        resId = R.font.martianmono_semiexpanded_light,
        weight = FontWeight.Light
    ),
    Font(
        resId = R.font.martianmono_semiexpanded_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.martianmono_semiexpanded_regular,
    ),
    Font(
        resId = R.font.martianmono_semiexpanded_semibold,
        weight = FontWeight.SemiBold
    ),
)

val baseline = Typography()
val WizzardTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = MartianMono), // 57sp / 64sp
    displayMedium = baseline.displayMedium.copy(fontFamily = MartianMono), // 45sp / 52sp
    displaySmall = baseline.displaySmall.copy(fontFamily = MartianMono), // 36sp / 44sp
    headlineLarge = baseline.headlineLarge.copy(fontFamily = MartianMono), // 32sp / 40sp
    headlineMedium = baseline.headlineMedium.copy(fontFamily = MartianMono), // 28sp / 36sp
    headlineSmall = baseline.headlineSmall.copy(fontFamily = MartianMono), // 24sp / 32sp
    titleLarge = baseline.titleLarge.copy(fontFamily = MartianMono), // 22sp / 28sp
    titleMedium = baseline.titleMedium.copy(fontFamily = MartianMono), // 16sp / 24sp
    titleSmall = baseline.titleSmall.copy(fontFamily = MartianMono), // 14sp / 20sp
    bodyLarge = baseline.bodyLarge.copy(fontFamily = MartianMono), // 16sp / 24sp
    bodyMedium = baseline.bodyMedium.copy(fontFamily = MartianMono), // 14sp / 20sp
    bodySmall = baseline.bodySmall.copy(fontFamily = MartianMono), // 12sp / 16sp
    labelLarge = baseline.labelLarge.copy(fontFamily = MartianMono), // 14sp / 20sp
    labelMedium = baseline.labelMedium.copy(fontFamily = MartianMono), // 12sp / 16sp
    labelSmall = baseline.labelSmall.copy(fontFamily = MartianMono), // 11sp / 16sp
)