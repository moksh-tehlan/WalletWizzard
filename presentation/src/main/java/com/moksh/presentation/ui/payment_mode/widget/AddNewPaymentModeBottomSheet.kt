package com.moksh.presentation.ui.payment_mode.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.ui.category.widget.AddNewCategoryBottomSheet
import com.moksh.presentation.ui.common.Gap
import com.moksh.presentation.ui.common.WizzardPrimaryButton
import com.moksh.presentation.ui.common.WizzardTextField

@Composable
fun AddNewPaymentModeBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    value: String,
    onValueChange: (String) -> Unit,
    isButtonLoading: Boolean = false,
    onSave: () -> Unit,
) {
    ModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp)
                .imePadding()
        ) {
            WizzardTextField(
                heading = "Add new payment mode",
                modifier = Modifier.fillMaxWidth(),
                value = value,
                onValueChange = onValueChange
            )
            Gap(20.dp)
            WizzardPrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                isLoading = isButtonLoading,
                text = "SAVE",
                onClick = {
                    onSave()
                }
            )
        }
    }
}

@Composable
@Preview
private fun AddNewPaymentModeBottomSheetPreview() {
    WalletWizzardTheme {
        AddNewCategoryBottomSheet(
            value = "Dont Know",
            onValueChange = {},
            onDismissRequest = {},
            sheetState = rememberModalBottomSheetState(),
            onSave = {}
        )
    }
}