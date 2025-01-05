package com.moksh.presentation.ui.savings.new_pocket

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.core.theme.WizzardWhite
import com.moksh.presentation.core.theme.backArrowIcon
import com.moksh.presentation.core.utils.DatePatterns
import com.moksh.presentation.core.utils.toFormattedTime
import com.moksh.presentation.ui.common.Gap
import com.moksh.presentation.ui.common.GapSpace
import com.moksh.presentation.ui.common.ObserveAsEvents
import com.moksh.presentation.ui.common.WizzardDatePicker
import com.moksh.presentation.ui.common.WizzardPrimaryButton
import com.moksh.presentation.ui.common.WizzardTextField
import com.moksh.presentation.ui.savings.new_pocket.viewmodel.AddNewPocketAction
import com.moksh.presentation.ui.savings.new_pocket.viewmodel.AddNewPocketEvent
import com.moksh.presentation.ui.savings.new_pocket.viewmodel.AddNewPocketState
import com.moksh.presentation.ui.savings.new_pocket.viewmodel.AddNewPocketViewModel

@Composable
fun AddNewSavingsPocketScreen(
    viewModel: AddNewPocketViewModel = hiltViewModel(),
    onCreatingSuccess: () -> Unit,
) {
    ObserveAsEvents(viewModel.addNewPocketFlow) { event ->
        when (event) {
            is AddNewPocketEvent.CreatePocketSuccess -> onCreatingSuccess()
        }
    }
    val state = viewModel.addNewPocketState.collectAsStateWithLifecycle().value;
    AddNewSavingsPocketScreenView(
        state = state,
        onAction = viewModel::onAction,
    )
}

@Composable
private fun AddNewSavingsPocketScreenView(
    state: AddNewPocketState,
    onAction: (AddNewPocketAction) -> Unit,
) {
    if (state.showDatePicker) {
        WizzardDatePicker(
            onDateSelected = { date ->
                date?.let { onAction(AddNewPocketAction.OnDateChange(it)) }
            },
            onDismiss = { onAction(AddNewPocketAction.OnToggleDatePicker) }
        )
    }
    Scaffold(
        topBar = {
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
                Gap(size = GapSpace.MAX)
                Text(
                    text = "Add New Pocket",
                    style = MaterialTheme.typography.titleMedium.copy(color = WizzardWhite)
                )
                Spacer(modifier = Modifier.weight(1.25f))
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(15.dp)
                .padding(innerPadding)
        ) {
            WizzardTextField(
                heading = "POCKET NAME",
                hint = "Enter pocket name",
                value = state.pocketName,
                onValueChange = { name ->
                    onAction(AddNewPocketAction.OnPocketNameChange(name))
                },
            )
            Gap(size = 15.dp)
            WizzardTextField(
                heading = "AMOUNT",
                hint = "Enter amount",
                value = state.amount,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                onValueChange = { amount ->
                    onAction(AddNewPocketAction.OnAmountChange(amount))
                },
            )
            Gap(size = 15.dp)
            WizzardTextField(
                heading = "END DATE",
                hint = "Select end date",
                value = state.date?.toFormattedTime(DatePatterns.ShortDatePattern) ?: "",
                onValueChange = { },
                enabled = false,
                onClick = {
                    onAction(AddNewPocketAction.OnToggleDatePicker)
                },
            )
            Gap(size = GapSpace.MAX)
            WizzardPrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                text = "ADD NEW POCKET",
                onClick = {
                    onAction(AddNewPocketAction.OnAddNewPocket)
                },
                isLoading = state.isButtonLoading,
                enabled = true,
            )
            Gap(size = 15.dp)
        }
    }
}

@Composable
@Preview
private fun AddNewSavingsPocketScreenPreview() {
    WalletWizzardTheme {
        AddNewSavingsPocketScreenView(
            state = AddNewPocketState(),
            onAction = {}
        )
    }
}