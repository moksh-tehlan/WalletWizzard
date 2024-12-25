package com.moksh.presentation.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.applyIf(condition: Boolean, block: @Composable Modifier.() -> Modifier): Modifier {
    return if (condition) {
        then(block(Modifier))
    } else {
        this
    }
}