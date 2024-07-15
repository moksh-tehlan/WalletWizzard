package com.moksh.presentation.ui.profile_tab

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.core.theme.WizzardRed
import com.moksh.presentation.core.theme.editIcon
import com.moksh.presentation.ui.common.Gap
import com.moksh.presentation.ui.profile_tab.components.AdditionalDataText
import com.moksh.presentation.ui.profile_tab.components.ProfileDataBox

@Composable
fun ProfileTab(
    onEditProfileClick: () -> Unit,
) {
    ProfileTabView(
        onEditProfileClick = onEditProfileClick
    )
}


@Composable
private fun ProfileTabView(
    onEditProfileClick: () -> Unit,
) {
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "Wallet-Wizzard",
                    style = MaterialTheme.typography.titleMedium,
                )
                Icon(
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onEditProfileClick()
                    },
                    imageVector = editIcon, contentDescription = "Edit Button"
                )
            }
            Gap(size = 70.dp)
            ProfileDataBox(heading = "FULL NAME", content = "MOKSH TEHLAN")
            Gap(size = 20.dp)
            ProfileDataBox(heading = "DATE OF BIRTH", content = "03/06/2003")
            Gap(size = 20.dp)
            ProfileDataBox(heading = "PHONE NUMBER", content = "7015472985")
            Gap(size = 20.dp)
            ProfileDataBox(heading = "EMAIL", content = "tehlanmoksh@gmail.com")
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


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview
private fun ProfileTabPreview() {
    WalletWizzardTheme {
        Scaffold {
            ProfileTabView(
                onEditProfileClick = {}
            )
        }
    }
}