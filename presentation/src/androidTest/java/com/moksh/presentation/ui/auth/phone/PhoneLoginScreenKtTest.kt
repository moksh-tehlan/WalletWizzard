package com.moksh.presentation.ui.auth.phone

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.moksh.presentation.ui.auth.phone.viewmodel.PhoneLoginAction
import com.moksh.presentation.ui.auth.phone.viewmodel.PhoneLoginState
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class PhoneLoginScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testPhoneLoginScreenView_InitialState() {
        composeTestRule.setContent {
            PhoneLoginScreenView(
                onAction = {},
                state = PhoneLoginState()
            )
        }
        composeTestRule.onNodeWithText("Wallet-Wizzard").assertIsDisplayed()
        composeTestRule.onNodeWithText("LET'S GET YOU STARTED!").assertIsDisplayed()
        composeTestRule.onNodeWithText("Please tell us your phone number to begin")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("CONTINUE").assertIsDisplayed()
    }

    @Test
    fun testPhoneLoginScreenView_LoadingState() {
        composeTestRule.setContent {
            PhoneLoginScreenView(
                onAction = {},
                state = PhoneLoginState(isLoading = true)
            )
        }

        composeTestRule.onNodeWithTag("buttonLoader").assertIsDisplayed()
    }

    @Test
    fun testPhoneLoginScreenView_ButtonDisabledState() {
        composeTestRule.setContent {
            PhoneLoginScreenView(
                onAction = {},
                state = PhoneLoginState(buttonEnabled = false)
            )
        }

        composeTestRule.onNodeWithText("CONTINUE").assertIsNotEnabled()
    }

    @Test
    fun testPhoneLoginScreenView_PhoneNumberInput() {
        var phoneNumber = ""
        composeTestRule.setContent {
            PhoneLoginScreenView(
                onAction = { action ->
                    if (action is PhoneLoginAction.ChangePhoneNumber) {
                        phoneNumber = action.phoneNumber
                    }
                },
                state = PhoneLoginState()
            )
        }

        composeTestRule.onNodeWithTag("phoneNumberTextField") // Replace with your actual tag
            .performTextInput("1234567890")

        assertEquals("1234567890", phoneNumber)
    }

    @Test
    fun testPhoneLoginScreenView_ContinueButtonClick() {
        var continueClicked = false
        composeTestRule.setContent {
            PhoneLoginScreenView(
                onAction = { action ->
                    if (action is PhoneLoginAction.OnContinue) {
                        continueClicked = true
                    }
                },
                state = PhoneLoginState(buttonEnabled = true)
            )
        }

        composeTestRule.onNodeWithTag("continueButton").performClick()

        assert(continueClicked)
    }
}