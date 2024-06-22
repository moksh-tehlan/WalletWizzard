package com.moksh.presentation.ui.books_tab

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.core.theme.WizzardBlue
import com.moksh.presentation.core.theme.WizzardRed
import com.moksh.presentation.core.theme.WizzardYellow
import com.moksh.presentation.core.theme.passBookIcon
import com.moksh.presentation.ui.books_tab.components.BooksViewPager
import com.moksh.presentation.ui.common.Gap
import kotlinx.coroutines.launch

@Composable
fun BooksTab() {
    BooksTabView()
}

@Composable
private fun BooksTabView() {
    val pagerState = rememberPagerState(pageCount = { 2 }, initialPage = 0)
    val tabs = listOf("Credits", "Debts")
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(currentTabPosition = tabPositions[pagerState.currentPage]),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                )
            },
            divider = {}
        ) {
            tabs.forEachIndexed { index, tabTitle ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                        .clickable(
                            enabled = index != pagerState.currentPage,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) {
                            scope.launch { pagerState.animateScrollToPage(index) }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tabTitle,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
        HorizontalPager(
            state = pagerState,
        ) {
            val (amountColor, description) = when (it) {
                1 -> WizzardRed to "Total Debts"
                else -> WizzardYellow to "Total Credits"
            }
            BooksViewPager(
                amount = "10,000",
                amountColor = amountColor,
                description = description,
                count = 10
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(3.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(10.dp)
                ) {
                    Row {
                        Icon(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(WizzardBlue.copy(0.22f))
                                .padding(10.dp),
                            imageVector = passBookIcon, contentDescription = "Book Icon",
                            tint = WizzardBlue
                        )
                        Gap(size = 10.dp)
                        Column(
                            modifier = Modifier.weight(2.5f)
                        ) {
                            Text(
                                text = "February Expenses",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Gap(size = 5.dp)
                            Text(
                                text = "Updated 20 hours ago",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.onBackground.copy(
                                        0.5f
                                    ),
                                    fontSize = 9.sp
                                )
                            )
                        }
                        Gap(size = 10.dp)
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "10,000",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.End,
                            color = amountColor
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview
private fun BooksTabPreview() {
    WalletWizzardTheme { Scaffold { BooksTabView() } }
}