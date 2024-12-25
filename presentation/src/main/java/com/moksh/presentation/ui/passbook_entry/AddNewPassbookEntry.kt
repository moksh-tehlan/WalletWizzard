package com.moksh.presentation.ui.passbook_entry

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moksh.domain.model.response.PaymentMode
import com.moksh.domain.model.response.TransactionType
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.core.theme.WizzardWhite
import com.moksh.presentation.core.theme.backArrowIcon
import com.moksh.presentation.ui.common.Gap
import com.moksh.presentation.ui.common.GapSpace
import com.moksh.presentation.ui.common.ObserveAsEvents
import com.moksh.presentation.ui.common.WizzardPrimaryButton
import com.moksh.presentation.ui.common.WizzardTextField
import com.moksh.presentation.ui.passbook_entry.viewmodel.PassbookEntryAction
import com.moksh.presentation.ui.passbook_entry.viewmodel.PassbookEntryEvent
import com.moksh.presentation.ui.passbook_entry.viewmodel.PassbookEntryState
import com.moksh.presentation.ui.passbook_entry.viewmodel.PassbookEntryViewModel

@Composable
fun AddNewPassbookEntry(
    viewModel: PassbookEntryViewModel = hiltViewModel(),
    onTransactionSave: () -> Unit,
    onSelectCategory: (transactionType: TransactionType, categoryId: String?) -> Unit,
    onPaymentModeChange: (paymentModeId: String?) -> Unit,
    selectedCategoryId: String? = null,
    selectedPaymentModeId: String? = null,
) {
    val context = LocalContext.current
    ObserveAsEvents(flow = viewModel.passbookEvent) {
        when (it) {
            is PassbookEntryEvent.TransactionSaved -> {
                Toast.makeText(context, "Transaction Saved", Toast.LENGTH_SHORT).show()
                onTransactionSave()
            }

            is PassbookEntryEvent.OnCategoryChange -> onSelectCategory(
                it.transactionType,
                it.categoryId
            )

            is PassbookEntryEvent.OnPaymentModeChange -> onPaymentModeChange(it.paymentModeId)
        }
    }
    LaunchedEffect(selectedCategoryId) {
        viewModel.onAction(
            PassbookEntryAction.UpdateSelectedCategoryAndPaymentMode(
                categoryId = selectedCategoryId,
                paymentId = selectedPaymentModeId
            )
        )
    }
    AddNewPassbookEntryView(
        state = viewModel.passbookEntryState.collectAsState().value,
        onAction = viewModel::onAction
    )
}

@Composable
private fun AddNewPassbookEntryView(
    state: PassbookEntryState,
    onAction: (PassbookEntryAction) -> Unit,
) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            val topBarTitle = when (state.entryType) {
                TransactionType.Income -> "Add New Income"
                TransactionType.Expenses -> "Add New Expense"
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .height(70.dp)
                    .padding(horizontal = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = backArrowIcon,
                    contentDescription = "Back Arrow",
                    tint = WizzardWhite
                )
                Gap(gapSpace = GapSpace.MAX)
                Text(
                    text = topBarTitle,
                    style = MaterialTheme.typography.titleMedium.copy(color = WizzardWhite)
                )
                Spacer(modifier = Modifier.weight(1.25f))
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .imePadding()
        ) {
            WizzardTextField(
                heading = "AMOUNT",
                value = state.amount.toString(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                onValueChange = { onAction(PassbookEntryAction.AmountChanged(it)) },
            )
            Gap(size = 15.dp)
            WizzardTextField(
                heading = "Remark",
                value = state.remark,
                hint = "Remark (Item, Person Name, Quantity etc.)",
                singleLine = false,
                onValueChange = { onAction(PassbookEntryAction.RemarkChanged(it)) },
            )
            Gap(size = 15.dp)
            WizzardTextField(
                onClick = { onAction(PassbookEntryAction.OnCategoryChange) },
                heading = "Category",
                value = state.category?.name ?: "",
                hint = "Category",
                enabled = false,
                onValueChange = { },
            )
            Gap(size = 15.dp)
            WizzardTextField(
                onClick = { onAction(PassbookEntryAction.OnPaymentModeChange) },
                heading = "PaymentMode",
                value = state.paymentMode?.name ?: "",
                hint = "Payment Mode",
                enabled = false,
                onValueChange = { },
            )
            Gap(size = 40.dp)
            Gap(gapSpace = GapSpace.MAX)
            WizzardPrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                text = "SAVE",
                onClick = {
                    onAction(PassbookEntryAction.SaveTransaction)
                },
                enabled = true,
            )
        }
    }
}

@Composable
private fun PaymentMethodSelector(
    selectedMode: PaymentMode?,
    paymentModes: List<PaymentMode>,
    onChangePaymentMode: (PaymentMode) -> Unit,
) {
    Column {
        Text(
            text = "Payment Mode",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground.copy(.7f)
            )
        )
        Gap(size = 6.dp)
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(paymentModes) {
                PaymentModeChip(
                    mode = it,
                    isSelected = selectedMode == it,
                    onClick = onChangePaymentMode
                )
            }
        }
    }
}

@Composable
private fun PaymentModeChip(
    mode: PaymentMode,
    isSelected: Boolean = false,
    onClick: (PaymentMode) -> Unit,
) {
    val backgroundColor =
        if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
    val textColor =
        if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onBackground
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(backgroundColor)
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .clickable {
                onClick(mode)
            }
    ) {
        Text(text = mode.name, style = MaterialTheme.typography.bodyMedium.copy(color = textColor))
    }
}

@Composable
@Preview
private fun AddNewPassbookEntryPreview() {
    WalletWizzardTheme {
        AddNewPassbookEntryView(
            state = PassbookEntryState(),
            onAction = {}
        )
    }
}