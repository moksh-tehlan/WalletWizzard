package com.moksh.presentation.ui.edit_profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.core.theme.backIcon
import com.moksh.presentation.ui.common.Gap
import com.moksh.presentation.ui.common.GapSpace
import com.moksh.presentation.ui.common.WizzardPrimaryButton
import com.moksh.presentation.ui.profile_tab.components.ProfileEditTextField

@Composable
fun EditProfileScreen() {
    EditProfileScreenView()
}

@Composable
private fun EditProfileScreenView() {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, top = 30.dp, bottom = 20.dp)
                .padding(paddingValues)
        ) {
            Icon(
                modifier = Modifier,
                imageVector = backIcon, contentDescription = "back button"
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
            Gap(gapSpace = GapSpace.MAX)
            WizzardPrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                text = "SAVE DETAILS", onClick = {})
        }
    }
}

@Composable
@Preview
private fun EditProfileScreenPreview() {
    WalletWizzardTheme { EditProfileScreenView() }
}