package com.moksh.presentation.ui.home_tab.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.core.theme.WizzardGreen
import com.moksh.presentation.core.theme.forwardArrowIcon
import com.moksh.presentation.ui.common.Gap

@Composable
fun OverviewCard(
    modifier: Modifier = Modifier,
    name: String,
    amount: String,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(3.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(10.dp)
    ) {
        Text(
            text = name, style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Gap(size = 10.dp)
        Row {
            Text(
                modifier = Modifier.weight(1f),
                text = amount, style = MaterialTheme.typography.bodyLarge.copy(
                    color = WizzardGreen
                )
            )
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = forwardArrowIcon,
                contentDescription = "forward arrow",
                tint = MaterialTheme.colorScheme.onBackground.copy(0.5f)
            )
        }
    }
}

@Composable
@Preview
private fun OverviewCardPreview() {
    WalletWizzardTheme {
        Scaffold {
            Row(
                modifier = Modifier
                    .padding(it)
                    .fillMaxWidth()
            ) {
                OverviewCard(
                    modifier = Modifier.weight(1f),
                    name = "Normal Savings",
                    amount = "10,000"
                )
                Gap(size = 5.dp)
                OverviewCard(
                    modifier = Modifier.weight(1f),
                    name = "Investments",
                    amount = "10,000"
                )
            }
        }
    }
}