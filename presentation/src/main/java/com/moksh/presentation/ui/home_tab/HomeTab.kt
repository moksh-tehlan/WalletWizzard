package com.moksh.presentation.ui.home_tab

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.core.theme.WizzardGreen
import com.moksh.presentation.core.theme.WizzardRed
import com.moksh.presentation.core.theme.WizzardYellow
import com.moksh.presentation.core.theme.addIcon
import com.moksh.presentation.ui.common.Gap
import com.moksh.presentation.ui.common.GapSpace
import com.moksh.presentation.ui.home_tab.components.BalanceItem
import com.moksh.presentation.ui.home_tab.components.SavingsCard

@Composable
fun HomeTab() {
    HomeTabView()
}

@Composable
private fun HomeTabView() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp)
            .padding(bottom = 100.dp)
            .verticalScroll(scrollState),
    ) {
        Gap(size = 30.dp)
        Text(
            text = "Wallet-Wizzard",
            style = MaterialTheme.typography.titleMedium,
        )
        Gap(size = 70.dp)
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(7.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 15.dp, vertical = 20.dp),
            ) {
                Column {
                    Gap(size = 5.dp)
                    Text(
                        text = "10,000",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = WizzardGreen,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Gap(size = 20.dp)
                    Row {
                        BalanceItem(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.Start,
                            instrument = "Savings",
                            amount = "10,000",
                            amountColor = WizzardGreen
                        )
                        BalanceItem(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.End,
                            instrument = "Investments",
                            amount = "10,000",
                            amountColor = WizzardGreen
                        )
                    }
                    Gap(size = 15.dp)
                    HorizontalDivider(
                        thickness = 1.dp
                    )
                    Gap(size = 15.dp)
                    Row {
                        BalanceItem(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.Start,
                            instrument = "Credits",
                            amount = "10,000",
                            amountColor = WizzardYellow
                        )
                        BalanceItem(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.End,
                            instrument = "Debts",
                            amount = "10,000",
                            amountColor = WizzardRed
                        )
                    }
                }

            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(y = (-15).dp, x = 15.dp)
                    .clip(RoundedCornerShape(3.5.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 7.dp, vertical = 5.dp)
            ) {
                Text(
                    text = "Total Balance", style = MaterialTheme.typography.labelSmall
                )
            }
        }
        Gap(size = 25.dp)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Savings Pocket", style = MaterialTheme.typography.bodyMedium)
            Gap(gapSpace = GapSpace.MAX)
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(
                        color = MaterialTheme.colorScheme.surface
                    )
                    .padding(5.dp),
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = addIcon,
                    contentDescription = "add icon",
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
        Gap(size = 15.dp)
        Column {
            List(
                size = 10,
                init = {
                    SavingsCard()
                    Gap(15.dp)
                }
            )
        }
    }
}

@Composable
@Preview
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
private fun HomeTabPreview() {
    WalletWizzardTheme { Scaffold { HomeTabView() } }
}