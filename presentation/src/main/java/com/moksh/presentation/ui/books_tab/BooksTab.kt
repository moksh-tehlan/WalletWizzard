package com.moksh.presentation.ui.books_tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.core.theme.WizzardBlack
import com.moksh.presentation.core.theme.WizzardBlue
import com.moksh.presentation.core.theme.WizzardRed
import com.moksh.presentation.core.theme.WizzardWhite
import com.moksh.presentation.core.theme.WizzardYellow
import com.moksh.presentation.core.theme.addIcon
import com.moksh.presentation.core.theme.passBookIcon
import com.moksh.presentation.ui.common.Gap
import com.moksh.presentation.ui.common.clickable
import com.moksh.presentation.ui.home_tab.components.BalanceItem
import kotlinx.coroutines.launch

@Composable
fun BooksTab() {
    BooksTabView()
}

@Composable
private fun BooksTabView() {
    val scrollState = rememberScrollState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (showBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                scope.launch {
                    sheetState.hide().also {
                        showBottomSheet = false
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(Modifier.fillMaxWidth().height(150.dp)){ Text(text = "Testing Sheet") }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(bottom = 180.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            BooksOverview()
            List(
                size = 10,
                init = {
                    val amountColor = if (it % 2 == 0) WizzardYellow else WizzardRed
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Max)
                            .padding(horizontal = 10.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(top = 10.dp, start = 10.dp, bottom = 10.dp)
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(WizzardBlue.copy(0.22f))
                                    .padding(10.dp),
                                imageVector = passBookIcon, contentDescription = "Book Icon",
                                tint = WizzardBlue
                            )
                            Gap(size = 10.dp)
                            Column(
                                modifier = Modifier
                                    .weight(2.5f)
                                    .padding(top = 10.dp, start = 10.dp, bottom = 10.dp)
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
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .background(amountColor.copy(0.04f))
                                    .weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "10,000",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = amountColor
                                )
                            }
                        }
                    }

                }
            )
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
                    scope.launch {
                        showBottomSheet = true
                        sheetState.expand()
                    }
                },
            imageVector = addIcon,
            tint = WizzardBlack,
            contentDescription = "Add"
        )
    }
}

@Composable
private fun BooksOverview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp, bottom = 10.dp)
            .clip(
                RoundedCornerShape(7.dp)
            )
            .background(MaterialTheme.colorScheme.surface)
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Overview",
                style = MaterialTheme.typography.bodyLarge.copy(
                    MaterialTheme.colorScheme.onBackground.copy(.7f)
                )
            )
            Gap(size = 20.dp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BalanceItem(
                    instrument = "Credits",
                    amount = "10,000",
                    amountColor = WizzardYellow,
                    horizontalAlignment = Alignment.Start,
                )
                BalanceItem(
                    instrument = "Debts",
                    amount = "10,000",
                    amountColor = WizzardRed,
                    horizontalAlignment = Alignment.End,
                )
            }
        }
    }
}

@Composable
@Preview
private fun BooksTabPreview() {
    WalletWizzardTheme { Scaffold {it -> BooksTabView() } }
}