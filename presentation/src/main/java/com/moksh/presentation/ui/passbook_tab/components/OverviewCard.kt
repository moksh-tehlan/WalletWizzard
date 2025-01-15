package com.moksh.presentation.ui.passbook_tab.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.core.theme.WizzardGreen
import com.moksh.presentation.core.theme.WizzardRed
import com.moksh.presentation.core.utils.toStringAmount
import com.moksh.presentation.ui.common.clickable
import kotlin.math.abs


@Composable
fun OverviewCard(
    modifier: Modifier = Modifier,
    monthlyIncome: Double,
    monthlyExpenses: Double,
    isExpanded: Boolean = false,
    onExpandClick: () -> Unit
) {
    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f, label = "rotation"
    )

    val netAmount = monthlyIncome - monthlyExpenses
    val isPositiveBalance = netAmount >= 0

    Box(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
        .clip(RoundedCornerShape(7.dp))
        .clickable { onExpandClick() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Header
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Overview",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f),
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                IconButton(
                    onClick = onExpandClick,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .rotate(rotationState)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Expand/Collapse",
                        tint = MaterialTheme.colorScheme.onSurface.copy(0.7f)
                    )
                }
            }

            // Expanded Content
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    // Money In and Money Out side by side
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Money In
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.Start
                        ) {

                            Text(
                                text = "Money In",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface.copy(0.7f)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = monthlyIncome.toStringAmount(),
                                style = MaterialTheme.typography.titleMedium,
                                color = WizzardGreen
                            )
                        }

                        // Vertical Divider
                        VerticalDivider(
                            modifier = Modifier
                                .height(60.dp)
                                .width(1.dp)
                                .align(Alignment.CenterVertically),
                            color = MaterialTheme.colorScheme.onSurface.copy(0.1f)
                        )

                        // Money Out
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "Money Out",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface.copy(0.7f)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = monthlyExpenses.toStringAmount(),
                                style = MaterialTheme.typography.titleMedium,
                                color = WizzardRed
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Net Amount
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Net Balance: ",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface.copy(0.7f)
                        )
                        Text(
                            text = buildAnnotatedString {
                                append(if (isPositiveBalance) "+" else "-")
                                append((abs(netAmount)).toStringAmount())
                            },
                            style = MaterialTheme.typography.titleLarge,
                            color = if (isPositiveBalance) WizzardGreen
                            else WizzardRed
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun OverviewCardPreview() {
    WalletWizzardTheme{
        OverviewCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(7.dp))
                .clickable { /*TODO*/ },
            monthlyIncome = 10000.0,
            monthlyExpenses = 5000.0,
            isExpanded = false
        ) {

        }
    }
}

@Composable
@Preview
fun OverviewCardExpandedPreview() {
    WalletWizzardTheme{
        OverviewCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(7.dp))
                .clickable { /*TODO*/ },
            monthlyIncome = 10000.0,
            monthlyExpenses = 5000.0,
            isExpanded = true
        ) {

        }
    }
}











