package com.moksh.presentation.ui.common

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

enum class GapSpace {
    MAX
}

@Composable
fun ColumnScope.Gap(size: Dp) {
    Spacer(modifier = Modifier.height(size))
}

@Composable
fun RowScope.Gap(size: Dp) {
    Spacer(modifier = Modifier.width(size))
}

@Composable
fun ColumnScope.Gap(size: GapSpace) {
    Spacer(modifier = Modifier.weight(1f))
}

@Composable
fun RowScope.Gap(size: GapSpace) {
    Spacer(modifier = Modifier.weight(1f))
}