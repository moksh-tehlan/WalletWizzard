package com.moksh.presentation.ui.home_tab.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.core.theme.WizzardCardBlue
import com.moksh.presentation.core.theme.WizzardCardGreen
import com.moksh.presentation.core.theme.WizzardCardPurple
import com.moksh.presentation.core.theme.WizzardLightGrey
import com.moksh.presentation.ui.common.Gap

@Composable
fun SavingsCard(
    modifier: Modifier = Modifier
) {
    val colors = listOf(WizzardCardBlue, WizzardCardGreen, WizzardCardPurple)
    val randomColor = colors.random()
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Row {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(color = randomColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "📱",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            Gap(size = 10.dp)
            Column(
                modifier = Modifier.height(70.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Apple Watch",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Text(
                            text = "13 Days Left",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.onBackground.copy(.5f)
                            )
                        )
                    }
                    Text(
                        text = "10,000",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )

                }
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    progress = { 0.5f },
                    color = randomColor,
                    trackColor = WizzardLightGrey
                )
            }
        }
    }
}

@Composable
@Preview
private fun SavingsCardPreview() {
    WalletWizzardTheme { SavingsCard() }
}