package com.moksh.presentation.ui.auth.otp

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
import com.moksh.presentation.ui.auth.otp.viewmodel.OtpVerificationAction
import com.moksh.presentation.ui.auth.otp.viewmodel.OtpVerificationEvent
import com.moksh.presentation.ui.auth.otp.viewmodel.OtpVerificationState
import com.moksh.presentation.ui.auth.otp.viewmodel.OtpVerificationViewModel
import com.moksh.presentation.ui.common.Gap
import com.moksh.presentation.ui.common.ObserveAsEvents
import com.moksh.presentation.ui.common.WizzardOtpField
import com.moksh.presentation.ui.common.WizzardPrimaryButton

@Composable
fun OtpVerificationScreen(
    viewModel: OtpVerificationViewModel = hiltViewModel(),
    onOtpVerified: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    ObserveAsEvents(flow = viewModel.events) { event ->
        when (event) {
            is OtpVerificationEvent.OtpVerified -> {
                keyboardController?.hide()
                onOtpVerified()
            }
        }
    }
    OtpVerificationScreenView(
        onAction = viewModel::onAction,
        state = viewModel.otpState.collectAsState().value,
    )
}

@Composable
fun OtpVerificationScreenView(
    onAction: (OtpVerificationAction) -> Unit,
    state: OtpVerificationState,
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
                text = "Please enter the otp received on",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                modifier = Modifier.alpha(0.7f),
                text = "+91-${state.phoneNumber}",
                style = MaterialTheme.typography.bodySmall
            )
            Gap(size = 70.dp)
            WizzardOtpField(
                onValueChange = { onAction(OtpVerificationAction.OnOtpChange(it)) }
            )
            Gap(size = 50.dp)
            WizzardPrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                text = "CONTINUE",
                isLoading = state.isLoading,
                enabled = state.buttonEnabled,
                onClick = { onAction(OtpVerificationAction.OnSubmitOtp) },
            )
        }
    }
}

@Composable
@Preview
fun OtpVerificationScreenPreview() {

}