package com.moksh.presentation.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.core.theme.WizzardRed
import com.moksh.presentation.core.theme.backIcon
import com.moksh.presentation.ui.common.Gap
import com.moksh.presentation.ui.common.WizzardPrimaryButton
import com.moksh.presentation.ui.profile.components.AdditionalDataText
import com.moksh.presentation.ui.profile.components.ProfileEditTextField

@Composable
fun ProfileScreen() {
    ProfileScreenView()
}

@Composable
private fun ProfileScreenView() {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(top = 20.dp)
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    modifier = Modifier,
                    imageVector = backIcon, contentDescription = "back button"
                )
                Spacer(modifier = Modifier.weight(0.8f))
                Text(text = "Profile")
                Spacer(modifier = Modifier.weight(1f))
            }
        },
        bottomBar = {
            WizzardPrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
                    .padding(horizontal = 20.dp)
                    .height(60.dp),
                text = "SAVE DETAILS", onClick = {})
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
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
            Gap(size = 60.dp)
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
                text = "LOGOUT",
                color = WizzardRed,
                onClick = {},
            )
            Gap(size = 10.dp)
        }
    }
}

@Composable
@Preview
private fun ProfileScreenPreview() {
    WalletWizzardTheme { ProfileScreenView() }
}