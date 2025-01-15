package com.moksh.presentation.ui.payment_mode

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moksh.domain.model.response.PaymentMode
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.core.theme.WizzardBlue
import com.moksh.presentation.core.theme.WizzardWhite
import com.moksh.presentation.core.theme.addIcon
import com.moksh.presentation.core.theme.backArrowIcon
import com.moksh.presentation.ui.common.Gap
import com.moksh.presentation.ui.common.GapSpace
import com.moksh.presentation.ui.common.ObserveAsEvents
import com.moksh.presentation.ui.common.WalletWizzardCheckBox
import com.moksh.presentation.ui.payment_mode.viewmodel.PaymentModeAction
import com.moksh.presentation.ui.payment_mode.viewmodel.PaymentModeDataState
import com.moksh.presentation.ui.payment_mode.viewmodel.PaymentModeEvent
import com.moksh.presentation.ui.payment_mode.viewmodel.PaymentModeState
import com.moksh.presentation.ui.payment_mode.viewmodel.PaymentModeViewModel
import com.moksh.presentation.ui.payment_mode.widget.AddNewPaymentModeBottomSheet

@Composable
fun PaymentModeScreen(
    viewModel: PaymentModeViewModel = hiltViewModel(),
    onBackPress:()->Unit,
    onPaymentModeSaved: (PaymentMode?) -> Unit
) {
    ObserveAsEvents(viewModel.paymentModeFlow) {event->
        when(event){
            is PaymentModeEvent.OnBackPress -> onBackPress()
            is PaymentModeEvent.OnPaymentModeChanged -> onPaymentModeSaved(event.paymentMode)
        }
    }
    PaymentModeScreenView(
        state = viewModel.paymentModeState.collectAsStateWithLifecycle().value,
        onAction = viewModel::onAction
    )
}

@Composable
private fun PaymentModeScreenView(
    state: PaymentModeState,
    onAction: (PaymentModeAction) -> Unit,
) {

    if (state.addNewCategorySheet != null) {
        AddNewPaymentModeBottomSheet(
            value = state.addNewCategorySheet.value,
            onValueChange = { onAction(PaymentModeAction.AddPaymentModeChanged(it)) },
            onDismissRequest = { onAction(PaymentModeAction.ToggleAddNewPaymentModeSheet) },
            isButtonLoading = state.addNewCategorySheet.isLoading,
            sheetState = rememberModalBottomSheetState(),
            onSave = { onAction(PaymentModeAction.SaveNewPaymentMode) }
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
                    modifier = Modifier.clickable(
                        onClick = {
                            onAction(PaymentModeAction.OnBackPress)
                        },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ),
                    imageVector = backArrowIcon,
                    contentDescription = "Back Arrow",
                    tint = WizzardWhite
                )
                Gap(size = GapSpace.MAX)
                Text(
                    text = "Choose Payment Mode",
                    style = MaterialTheme.typography.titleMedium.copy(color = WizzardWhite)
                )
                Spacer(modifier = Modifier.weight(1.25f))
            }
        },
        floatingActionButton = {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(55))
                    .background(
                        color = WizzardBlue
                    )
                    .clickable {
                        onAction(PaymentModeAction.ToggleAddNewPaymentModeSheet)
                    }
                    .padding(horizontal = 15.dp, vertical = 10.dp)
            ) {
                Icon(
                    imageVector = addIcon,
                    contentDescription = "Add Icon",
                    tint = WizzardWhite
                )
                Gap(5.dp)
                Text(
                    text = "ADD NEW",
                    style = MaterialTheme.typography.titleMedium.copy(color = WizzardWhite)
                )
            }
        }
    ) { paddingValues ->
        when (state.paymentModeDataState) {
            is PaymentModeDataState.Error -> {
                Text(text = state.paymentModeDataState.error)
            }

            is PaymentModeDataState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is PaymentModeDataState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    items(state.paymentModeDataState.paymentModes) { paymentMode ->
                        Row(
                            modifier = Modifier
                                .background(
                                    WizzardWhite.copy(
                                        alpha = .05f
                                    )
                                )
                                .clickable {
                                    onAction(PaymentModeAction.PaymentModeChanged(paymentMode))
                                }
                                .padding(horizontal = 10.dp, vertical = 15.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            WalletWizzardCheckBox(
                                checked = state.selectedPaymentMode == paymentMode,
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(text = paymentMode.name)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun PaymentModeScreenPreview() {
    WalletWizzardTheme {
        PaymentModeScreenView(
            state = PaymentModeState(
                paymentModeDataState = PaymentModeDataState.Success(
                    paymentModes = List(
                        6,
                        init = {
                            PaymentMode(
                                id = it.toString(),
                                name = "Payment Mode $it"
                            )
                        }
                    )
                )
            ),
            onAction = {}
        )
    }
}