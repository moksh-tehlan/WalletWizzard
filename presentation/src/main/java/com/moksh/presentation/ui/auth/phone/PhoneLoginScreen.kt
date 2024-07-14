package com.moksh.presentation.ui.auth.phone

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.ui.auth.phone.components.PhoneTextField
import com.moksh.presentation.ui.auth.phone.viewmodel.PhoneLoginAction
import com.moksh.presentation.ui.auth.phone.viewmodel.PhoneLoginEvent
import com.moksh.presentation.ui.auth.phone.viewmodel.PhoneLoginState
import com.moksh.presentation.ui.auth.phone.viewmodel.PhoneLoginViewModel
import com.moksh.presentation.ui.common.Gap
import com.moksh.presentation.ui.common.ObserveAsEvents
import com.moksh.presentation.ui.common.WizzardPrimaryButton

@Composable
fun PhoneLoginScreen(
    viewModel: PhoneLoginViewModel = hiltViewModel(),
    onOtpSent: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    ObserveAsEvents(flow = viewModel.events) { event ->
        when (event) {
            is PhoneLoginEvent.OnOtpSent -> {
                keyboardController?.hide()
                onOtpSent(event.phoneNumber)
            }
        }
    }
    PhoneLoginScreenView(
        onAction = viewModel::onAction,
        state = viewModel.phoneLoginState.collectAsState().value,
    )
}

@Composable
fun PhoneLoginScreenView(
    onAction: (PhoneLoginAction) -> Unit,
    state: PhoneLoginState,
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(25.dp),
        ) {
            Text(
                text = "Wallet-Wizzard",
                style = MaterialTheme.typography.titleMedium,
            )
            Gap(size = 80.dp)
            Text(
                text = "LET'S GET YOU STARTED!",
                style = MaterialTheme.typography.titleMedium
            )
            Gap(size = 10.dp)
            Text(
                modifier = Modifier.alpha(0.7f),
                text = "Please tell us your phone number to begin",
                style = MaterialTheme.typography.bodySmall
            )
            Gap(size = 70.dp)
            PhoneTextField(
                value = state.phoneNumber,
                testTag = "phoneNumberTextField",
                onValueChange = { onAction(PhoneLoginAction.ChangePhoneNumber(it)) })
            Gap(size = 50.dp)
            WizzardPrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                text = "CONTINUE",
                buttonTestTag = "continueButton",
                loaderTestTag = "buttonLoader",
                isLoading = state.isLoading,
                enabled = state.buttonEnabled,
                onClick = { onAction(PhoneLoginAction.OnContinue) },
            )
        }
    }
}

@Preview
@Composable
private fun PhoneLoginScreenPreview() {
    WalletWizzardTheme {
        PhoneLoginScreenView(
            onAction = {},
            state = PhoneLoginState()
        )
    }
}