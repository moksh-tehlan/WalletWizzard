package com.moksh.presentation.ui.auth.phone.viewmodel

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PhoneLoginViewModelTest {
    private lateinit var viewModel: PhoneLoginViewModel

    @Before
    fun setUp() {
        viewModel = PhoneLoginViewModel()
    }

    @Test
    fun `changePhoneNumber updates state with valid number`() {
        val testPhoneNumber = "1234567890"
        viewModel.onAction(PhoneLoginAction.ChangePhoneNumber(testPhoneNumber))

        val state = viewModel.phoneLoginState.value
        assertThat(state.phoneNumber).isEqualTo(testPhoneNumber)
        assertThat(state.buttonEnabled).isTrue()
    }

    @Test
    fun `changePhoneNumber updates state with invalid number`() {
        val testPhoneNumber = "123"
        viewModel.onAction(PhoneLoginAction.ChangePhoneNumber(testPhoneNumber))

        val state = viewModel.phoneLoginState.value
        assertThat(state.phoneNumber).isEqualTo(testPhoneNumber)
        assertThat(state.buttonEnabled).isFalse()
    }

    @Test
    fun `onContinue emits OnOtpSent event and updates state`() = runBlocking {

        val testPhoneNumber = "1234567890"
        viewModel.onAction(PhoneLoginAction.ChangePhoneNumber(testPhoneNumber))
        viewModel.onAction(PhoneLoginAction.OnContinue)

        viewModel.events.collectLatest {
            assertThat(it).isInstanceOf(PhoneLoginEvent.OnOtpSent::class.java)
            assertThat((it as PhoneLoginEvent.OnOtpSent).phoneNumber).isEqualTo(testPhoneNumber)
        }


        assertThat(viewModel.phoneLoginState.value.isLoading).isFalse()
        assertThat(viewModel.phoneLoginState.value.buttonEnabled).isTrue()
    }
}