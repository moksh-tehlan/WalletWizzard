package com.moksh.presentation.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.core.theme.WizzardWhite

@Composable
fun WalletWizzardCheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)? = null
) {
    Box(
        modifier = modifier
            .size(20.dp)
            .clip(CircleShape)
            .border(1.25.dp, color = WizzardWhite, shape = CircleShape)
            .applyIf(onCheckedChange != null){
                this.clickable(
                    onClick = { onCheckedChange?.invoke(checked.not()) },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
            }
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = checked,
            enter = fadeIn(animationSpec = tween(200)),
            exit = fadeOut(animationSpec = tween(200))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(WizzardWhite)
            )
        }
    }
}

@Composable
@Preview
private fun WalletWizzardCheckBoxPreview() {
    WalletWizzardTheme {
        WalletWizzardCheckBox(
            checked = true,
            onCheckedChange = {}
        )
    }
}