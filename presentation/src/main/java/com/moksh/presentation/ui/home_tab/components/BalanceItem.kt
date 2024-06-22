package com.moksh.presentation.ui.home_tab.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.moksh.presentation.ui.common.Gap

@Composable
fun BalanceItem(
    modifier: Modifier = Modifier,
    instrument: String,
    amount: String,
    amountColor: Color,
    horizontalAlignment: Alignment.Horizontal
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = instrument,
            style = MaterialTheme.typography.labelMedium.copy(
                color = MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.6f
                )
            )
        )
        Gap(size = 5.dp)
        Text(
            text = "₹$amount",
            style = MaterialTheme.typography.labelLarge.copy(
                color = amountColor
            )
        )
    }
}