package com.moksh.presentation.ui.passbook_tab

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moksh.domain.model.response.TransactionType
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.core.theme.WizzardBlack
import com.moksh.presentation.core.theme.WizzardGreen
import com.moksh.presentation.core.theme.WizzardRed
import com.moksh.presentation.core.theme.WizzardWhite
import com.moksh.presentation.core.theme.addIcon
import com.moksh.presentation.core.utils.toFormattedTime
import com.moksh.presentation.core.utils.toStringAmount
import com.moksh.presentation.ui.passbook_tab.components.PassbookItem
import com.moksh.presentation.ui.passbook_tab.components.PassbookViewPager
import com.moksh.presentation.ui.passbook_tab.viewmodel.PassBookEntryState
import com.moksh.presentation.ui.passbook_tab.viewmodel.PassbookViewModel
import kotlinx.coroutines.launch

@Composable
fun PassbookTab(
    onNewEntry: (TransactionType) -> Unit, viewModel: PassbookViewModel = hiltViewModel()
) {
    val viewmodelState = viewModel.passbookState.collectAsStateWithLifecycle().value
    PassbookTabView(
        onNewEntry = onNewEntry,
        incomeState = viewmodelState.incomeState,
        expenseState = viewmodelState.expenseState,
    )
}

@Composable
private fun PassbookTabView(
    onNewEntry: (TransactionType) -> Unit,
    incomeState: PassBookEntryState,
    expenseState: PassBookEntryState
) {
    val pagerState = rememberPagerState(pageCount = { 2 }, initialPage = 0)
    val tabs = listOf("Income", "Expenses")
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            TabRow(selectedTabIndex = pagerState.currentPage,
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(currentTabPosition = tabPositions[pagerState.currentPage]),
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
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
            HorizontalPager(
                state = pagerState,
            ) {
                if (it == 0) {
                    PassbookViewPager(
                        modifier = Modifier.fillMaxSize(),
                        amount = incomeState.currentMonthSum.toStringAmount(),
                        amountColor = WizzardGreen,
                        description = "Contribution this month",
                        getDate = {transaction -> transaction.createdAt},
                        data = incomeState.transactionList
                    ) { pageIndex, data ->
                        PassbookItem(
                            amount = data.amount.toStringAmount(),
                            amountColor = WizzardGreen,
                            remark = data.remark ?: "",
                            category = data.category?.name ?: "",
                            entryTime = data.createdAt.toFormattedTime()
                        )
                    }
                } else {
                    PassbookViewPager(
                        modifier = Modifier.fillMaxSize(),
                        amount = expenseState.currentMonthSum.toStringAmount(),
                        amountColor = WizzardRed,
                        description = "Contribution this month",
                        getDate = {transaction -> transaction.createdAt},
                        data = expenseState.transactionList
                    ) { pageIndex, data ->
                        PassbookItem(
                            amount = data.amount.toStringAmount(),
                            amountColor = WizzardRed,
                            remark = data.remark ?: "",
                            category = data.category?.name ?: "",
                            entryTime = data.createdAt.toFormattedTime()
                        )
                    }
                }
            }
        }
        Icon(
            modifier = Modifier
                .padding(bottom = 120.dp, end = 10.dp)
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
                expenseState = PassBookEntryState(),
                incomeState = PassBookEntryState()
            )
        }
    }
}