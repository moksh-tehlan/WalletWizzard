package com.moksh.presentation.ui.savings.new_pocket

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.core.theme.WizzardWhite
import com.moksh.presentation.core.theme.backArrowIcon
import com.moksh.presentation.ui.common.Gap
import com.moksh.presentation.ui.common.GapSpace
import com.moksh.presentation.ui.common.WizzardPrimaryButton
import com.moksh.presentation.ui.home_tab.components.SavingsPocketTextField
import com.moksh.presentation.ui.profile_tab.components.ProfileEditTextField

@Composable
fun AddNewSavingsPocketScreen() {
    AddNewSavingsPocketScreenView()
}

@Composable
private fun AddNewSavingsPocketScreenView() {
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
                Gap(gapSpace = GapSpace.MAX)
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
            SavingsPocketTextField(
                heading = "POCKET NAME",
                value = "Samsung watch ultra",
                onValueChange = { },
            )
            Gap(size = 15.dp)
            SavingsPocketTextField(
                heading = "AMOUNT",
                value = "10,000",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                onValueChange = { },
            )
            Gap(size = 15.dp)
            ProfileEditTextField(
                heading = "END DATE",
                value = "12/01/2023",
                onValueChange = { },
                enabled = false,
                onClick = { },
            )
            Gap(gapSpace = GapSpace.MAX)
            WizzardPrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                text = "ADD NEW POCKET",
                onClick = {},
                enabled = true,
            )
            Gap(size = 15.dp)
        }
    }
}

@Composable
@Preview
private fun AddNewSavingsPocketScreenPreview() {
    WalletWizzardTheme { AddNewSavingsPocketScreenView() }
}