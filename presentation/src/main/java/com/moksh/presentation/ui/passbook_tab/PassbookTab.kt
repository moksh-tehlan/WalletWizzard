@file:OptIn(ExperimentalFoundationApi::class)

package com.moksh.presentation.ui.passbook_tab

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moksh.domain.model.response.Transaction
import com.moksh.domain.model.response.TransactionType
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.core.theme.WizzardBlack
import com.moksh.presentation.core.theme.WizzardGreen
import com.moksh.presentation.core.theme.WizzardRed
import com.moksh.presentation.core.theme.WizzardWhite
import com.moksh.presentation.core.theme.addIcon
import com.moksh.presentation.core.utils.DatePatterns
import com.moksh.presentation.core.utils.toFormattedTime
import com.moksh.presentation.core.utils.toStringAmount
import com.moksh.presentation.ui.passbook_tab.components.OverviewCard
import com.moksh.presentation.ui.passbook_tab.components.PassbookItem
import com.moksh.presentation.ui.passbook_tab.viewmodel.PassBookEntryState
import com.moksh.presentation.ui.passbook_tab.viewmodel.PassbookState
import com.moksh.presentation.ui.passbook_tab.viewmodel.PassbookViewModel
import kotlinx.coroutines.launch

@Composable
fun PassbookTab(
    onNewEntry: (TransactionType) -> Unit, viewModel: PassbookViewModel = hiltViewModel()
) {
    val viewmodelState = viewModel.passbookState.collectAsStateWithLifecycle().value
    PassbookTabView(
        onNewEntry = onNewEntry, state = viewmodelState
    )
}

@Composable
fun PassbookTabView(
    onNewEntry: (TransactionType) -> Unit,
    state: PassbookState,
) {
    val pagerState = rememberPagerState(pageCount = { 2 }, initialPage = 0)
    val tabs = listOf("Income", "Expenses")
    val scope = rememberCoroutineScope()
    val isCardExpanded = remember { mutableStateOf(false) }
    val incomeState = state.incomeState
    val expenseState = state.expenseState


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Overview Card
            OverviewCard(
                modifier = Modifier.padding(top = 5.dp),
                monthlyIncome = state.monthlyIncome,
                monthlyExpenses = state.monthlyExpense,
                isExpanded = isCardExpanded.value,
                onExpandClick = { isCardExpanded.value = !isCardExpanded.value })

            // Sticky Header (Tab Row)
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(
                            currentTabPosition = tabPositions[pagerState.currentPage]
                        ), color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                    )
                },
                divider = {}) {
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
                            }, contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = tabTitle, style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            // Content
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalPager(
                    state = pagerState, modifier = Modifier.fillMaxSize()
                ) { page ->
                    val transactions = if (page == 0) {
                        incomeState.transactionList
                    } else {
                        expenseState.transactionList
                    }

                    val groupedData = transactions.groupBy { item ->
                        item.createdAt.toFormattedTime(DatePatterns.DatePattern)
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(top = 10.dp, bottom = 100.dp),
                    ) {
                        groupedData.forEach { (date, items) ->
                            stickyHeader {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colorScheme.background)
                                        .padding(vertical = 10.dp)
                                ) {
                                    Text(
                                        text = date,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = MaterialTheme.colorScheme.onBackground.copy(
                                                0.6f
                                            )
                                        ),
                                        modifier = Modifier.padding(horizontal = 20.dp)
                                    )
                                }
                            }

                            items(items) { item ->
                                PassbookItem(
                                    modifier = Modifier.padding(horizontal = 10.dp),
                                    amount = item.amount.toStringAmount(),
                                    amountColor = if (page == 0) WizzardGreen else WizzardRed,
                                    remark = item.remark ?: "",
                                    category = item.category?.name,
                                    entryTime = item.createdAt.toFormattedTime()
                                )
                            }
                        }
                    }
                }
            }
        }
        Icon(
            modifier = Modifier
                .padding(bottom = 100.dp, end = 10.dp)
                .size(50.dp)
                .clip(CircleShape)
                .background(WizzardWhite)
                .padding(5.dp)
                .align(Alignment.BottomEnd)
                .clickable {
                    if (pagerState.currentPage == 0) {
                        onNewEntry(TransactionType.Income)
                    } else {
                        onNewEntry(TransactionType.Expenses)
                    }
                }, imageVector = addIcon, tint = WizzardBlack, contentDescription = "Add"
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview
private fun PassbookTabPreview() {
    WalletWizzardTheme {
        Scaffold {
            PassbookTabView(
                onNewEntry = {},
                state = PassbookState(
                    expenseState = PassBookEntryState(transactionList = List(size = 10, init = {
                        Transaction(
                            id = "",
                            amount = 20000.0,
                            remark = "Helo there",
                            type = TransactionType.Expenses,
                            category = null,
                            paymentMode = null
                        )
                    })),
                    incomeState = PassBookEntryState(transactionList = List(size = 10, init = {
                        Transaction(
                            id = "",
                            remark = "Hi there",
                            amount = 20000.0,
                            type = TransactionType.Income,
                            category = null,
                            paymentMode = null
                        )
                    })),
                ),
            )
        }
    }
}