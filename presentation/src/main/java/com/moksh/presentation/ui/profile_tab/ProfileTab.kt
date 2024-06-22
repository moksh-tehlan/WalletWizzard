package com.moksh.presentation.ui.profile_tab

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.core.theme.WizzardRed
import com.moksh.presentation.ui.common.Gap
import com.moksh.presentation.ui.common.WizzardPrimaryButton
import com.moksh.presentation.ui.profile_tab.components.AdditionalDataText
import com.moksh.presentation.ui.profile_tab.components.ProfileEditTextField

@Composable
fun ProfileTab() {
    ProfileTabView()
}


@Composable
private fun ProfileTabView() {
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp)
            .padding(bottom = 100.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp)
                .verticalScroll(scrollState)
        ) {
            Gap(size = 30.dp)
            Text(
                text = "Wallet-Wizzard",
                style = MaterialTheme.typography.titleMedium,
            )
            Gap(size = 70.dp)
            ProfileEditTextField(
                heading = "FULL NAME",
                value = "Moksh Tehlan",
                onValueChange = { },
                onClick = {},
            )
            Gap(size = 20.dp)
            ProfileEditTextField(
                heading = "DATE OF BIRTH",
                value = "03/06/2003",
                onValueChange = { },
                enabled = false,
                onClick = { },
            )
            Gap(size = 20.dp)
            ProfileEditTextField(
                heading = "PHONE NUMBER",
                value = "7015472985",
                onValueChange = { },
                onClick = {},
            )
            Gap(size = 20.dp)
            ProfileEditTextField(
                heading = "EMAIL",
                value = "tehlanmoksh@gmail.com",
                onValueChange = { },
                onClick = {},
            )
            Gap(size = 20.dp)
            AdditionalDataText(
                text = "DISCLAIMER",
                onClick = {},
            )
            Gap(size = 10.dp)
            AdditionalDataText(
                text = "PRIVACY POLICY",
                onClick = {},
            )
            Gap(size = 10.dp)
            AdditionalDataText(
                text = "TERMS & CONDITIONS",
                onClick = {},
            )
            Gap(size = 10.dp)
            AdditionalDataText(
                text = "DELETE ACCOUNT",
                color = WizzardRed,
                onClick = {},
            )
            Gap(size = 10.dp)
        }
        WizzardPrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .height(60.dp)
                .align(Alignment.BottomCenter),
            text = "SAVE DETAILS", onClick = {})
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview
private fun ProfileTabPreview() {
    WalletWizzardTheme { Scaffold { ProfileTabView() } }
}