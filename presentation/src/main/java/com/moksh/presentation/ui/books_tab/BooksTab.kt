package com.moksh.presentation.ui.books_tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.core.theme.WizzardBlack
import com.moksh.presentation.core.theme.WizzardBlue
import com.moksh.presentation.core.theme.WizzardRed
import com.moksh.presentation.core.theme.WizzardWhite
import com.moksh.presentation.core.theme.WizzardYellow
import com.moksh.presentation.core.theme.addIcon
import com.moksh.presentation.core.theme.passBookIcon
import com.moksh.presentation.ui.books_tab.components.viewmodel.AddBookState
import com.moksh.presentation.ui.books_tab.components.viewmodel.BookState
import com.moksh.presentation.ui.books_tab.components.viewmodel.BooksAction
import com.moksh.presentation.ui.books_tab.components.viewmodel.BooksViewModel
import com.moksh.presentation.ui.common.Gap
import com.moksh.presentation.ui.common.WizzardPrimaryButton
import com.moksh.presentation.ui.common.WizzardTextField
import com.moksh.presentation.ui.common.clickable
import com.moksh.presentation.ui.home_tab.components.BalanceItem
import com.moksh.presentation.ui.passbook_entry.viewmodel.PassbookEntryAction
import kotlinx.coroutines.launch

@Composable
fun BooksTab(
    viewModel: BooksViewModel = hiltViewModel()
) {
    BooksTabView(
        state = viewModel.bookState.collectAsStateWithLifecycle().value,
        addNewBookState = viewModel.addBookState.collectAsStateWithLifecycle().value,
        onAction = viewModel::onAction
    )
}

@Composable
private fun BooksTabView(
    state: BookState,
    addNewBookState: AddBookState,
    onAction: (BooksAction) -> Unit,
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (state.addNewBottomSheetVisible) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                scope.launch {
                    sheetState.hide().also {
                        onAction(BooksAction.ToggleBottomSheet)
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
                WizzardTextField(
                    heading = "Add Book",
                    hint = "eg. Moksh's A/C",
                    value = addNewBookState.bookName,
                    onValueChange = { onAction(BooksAction.OnNameChange(it)) },
                )
                Gap(size = 30.dp)
                WizzardPrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    text = "SAVE",
                    isLoading = addNewBookState.buttonLoading,
                    onClick = {
                        onAction(BooksAction.OnSaveBook)
                    },
                    enabled = true,
                )
                Gap(size = WindowInsets.ime.asPaddingValues().calculateBottomPadding()/2)
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
                .padding(bottom = 100.dp, end = 10.dp)
                .size(50.dp)
                .clip(CircleShape)
                .background(WizzardWhite)
                .padding(5.dp)
                .align(Alignment.BottomEnd)
                .clickable {
                    scope.launch {
                        onAction(BooksAction.ToggleBottomSheet)
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
    WalletWizzardTheme {
        Scaffold { it ->
            BooksTabView(
                state = BookState(),
                addNewBookState = AddBookState(),
                onAction = {}
            )
        }
    }
}