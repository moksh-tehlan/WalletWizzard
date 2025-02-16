package com.moksh.presentation.ui.home_tab.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.ui.common.Gap
import com.moksh.presentation.ui.common.WizzardPrimaryButton
import com.moksh.presentation.ui.common.WizzardTextField
import com.moksh.presentation.ui.profile.components.ProfileEditTextField

@Composable
fun NewSavingsPocket(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    pocketName: String,
    amount: String,
    date: String,
    onDateChange: (String) -> Unit,
    pocketNameChange: (String) -> Unit,
    amountChange: (String) -> Unit,
    onAddNewPocket: () -> Unit
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        dragHandle = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp),
                text = "Add New Pocket", style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start
            )
        },
    ) {
        NewSavingsPocketView(
            pocketName = pocketName,
            amount = amount,
            date = date,
            pocketNameChange = pocketNameChange,
            amountChange = amountChange,
            onDateChange = onDateChange,
            onAddNewPocket = onAddNewPocket
        )
    }
}

@Composable
private fun NewSavingsPocketView(
    modifier: Modifier = Modifier,
    pocketName: String,
    amount: String,
    date: String,
    pocketNameChange: (String) -> Unit,
    amountChange: (String) -> Unit,
    onDateChange: (String) -> Unit,
    onAddNewPocket: () -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
            .verticalScroll(scrollState)
    ) {
        WizzardTextField(
            heading = "POCKET NAME",
            value = pocketName,
            onValueChange = pocketNameChange,
        )
        Gap(size = 15.dp)
        WizzardTextField(
            heading = "AMOUNT",
            value = amount,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            onValueChange = amountChange,
        )
        Gap(size = 15.dp)
        ProfileEditTextField(
            heading = "END DATE",
            value = date,
            onValueChange = onDateChange,
            enabled = false,
            onClick = { },
        )
        Gap(size = 15.dp)
        HorizontalDivider(
            color = Color.White.copy(0.3f),
            thickness = 1.dp
        )
        Gap(size = 15.dp)
        WizzardPrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            text = "ADD NEW POCKET",
            onClick = onAddNewPocket,
            enabled = true,
        )
        Gap(size = 20.dp)
    }
}


@Composable
@Preview
private fun NewSavingsPocketBottomSheetPreview() {
    WalletWizzardTheme {
        NewSavingsPocketView(
            pocketName = "Samsung watch ultra",
            amount = "70,000",
            date = "01/01/2023",
            onDateChange = {},
            pocketNameChange = {},
            amountChange = {},
            onAddNewPocket = {}
        )
    }
}