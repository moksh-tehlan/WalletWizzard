package com.moksh.presentation.ui.home_tab.components

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moksh.domain.model.response.Savings
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.core.theme.WizzardLightGrey
import com.moksh.presentation.core.utils.DatePatterns
import com.moksh.presentation.core.utils.toFormattedTime
import com.moksh.presentation.core.utils.toStringAmount
import com.moksh.presentation.ui.common.Gap
import java.util.Calendar
import java.util.Date
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SavingsCard(
    modifier: Modifier = Modifier,
    pocket: Savings,
) {
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
                    .background(color = Color(pocket.progressBarColor)),
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
                            text = pocket.name,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Text(
                            text = "${pocket.daysLeft?.toFormattedTime(DatePatterns.DaysPattern)} Days Left",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.onBackground.copy(.5f)
                            )
                        )
                    }
                    Text(
                        text = pocket.targetAmount.toStringAmount(),
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )

                }
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    progress = { pocket.progress },
                    color = Color(pocket.progressBarColor),
                    trackColor = WizzardLightGrey
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
private fun SavingsCardPreview() {
    WalletWizzardTheme {
        SavingsCard(
            pocket = Savings(
                id = UUID.randomUUID().toString(),  // Generate random UUID
                name = "Samsung Watch Ultra",
                targetAmount = 100000.0,
                currentAmount = 25000.0,  // 25% progress
                endDate = Calendar.getInstance().apply {
                    add(Calendar.MONTH, 3)  // 3 months from now
                }.time,
                progressBarColor = Color.Blue.toArgb(),  // Using Android's Color class
                notes = "Saving for my dream watch",
                isActive = true,
                isSynced = false,
                createdAt = Date(),  // Current date/time
                updatedAt = Date()   // Current date/time
            )
        )
    }
}