package com.moksh.presentation.ui.tab.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.moksh.presentation.ui.common.Gap

@Composable
fun WizzardNavigationItem(
    label: String,
    isActive: Boolean,
    onClick: () -> Unit,
    imageVector: ImageVector,
) {
    Row(
        modifier = Modifier
            .alpha(if (!isActive) 0.5f else 1f)
            .clickable(
                enabled = !isActive,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick()
            }, verticalAlignment = Alignment.CenterVertically
    ) {
        Image(imageVector = imageVector, contentDescription = label)
        AnimatedVisibility(visible = isActive) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Gap(size = 5.dp)
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}