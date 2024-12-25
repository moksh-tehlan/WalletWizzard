package com.moksh.presentation.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moksh.presentation.core.theme.WalletWizzardTheme

@Composable
fun WizzardOtpField(
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(
        color = MaterialTheme.colorScheme.onBackground
    ),
    onValueChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val text = remember {
        Array(6) { mutableStateOf("") }
    }
    val refs = remember { List(6) { FocusRequester() } }
    LaunchedEffect(key1 = Unit) {
        refs[0].requestFocus()
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(6) { index ->
            var isFocused by remember { mutableStateOf(false) }
            BasicTextField(
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(refs[index])
                    .focusProperties {
                        next = refs.getOrNull(index + 1) ?: refs.last()
                        previous = refs.getOrNull(index - 1) ?: refs.first()
                    }
                    .onKeyEvent {
                        if ((it.key == Key.Delete || it.key == Key.Backspace) && text[index].value.isEmpty()) {
                            focusManager.moveFocus(FocusDirection.Previous)
                            true
                        } else {
                            false
                        }
                    }
                    .onFocusChanged {
                        isFocused = it.isFocused
                    },
                value = text[index].value,
                enabled = true,
                onValueChange = { value ->
                    value.let {
                        if (text[index].value.isNotEmpty() && it.isEmpty()) {
                            text[index].value = ""
                        } else if (it.isEmpty()) {
                            focusManager.moveFocus(FocusDirection.Previous)
                        } else if (it.length == 2) {
                            if (index + 1 < 6) {
                                text[index + 1].value = it.last().toString()
                            }
                            focusManager.moveFocus(FocusDirection.Next)
                        } else {
                            if (it.length <= 1) {
                                text[index].value = it
                            }
                            if (it.length == 1) {
                                focusManager.moveFocus(FocusDirection.Next)
                            } else {
                                focusManager.moveFocus(FocusDirection.Previous)
                            }
                        }
                    }
                    val otp = text.map { map -> map.value.take(1) }
                        .filter { text -> text.isNotBlank() }
                        .joinToString("")
                    onValueChange(otp)
                },
                textStyle = textStyle.copy(
                    textAlign = TextAlign.Center
                ),
                cursorBrush = SolidColor(Color.White),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                singleLine = true
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    it.invoke()
                    if (text[index].value.isEmpty()) {
                        Text(
                            modifier = Modifier.alpha(0.2f),
                            text = "0",
                            style = textStyle
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun WizzardOtpFieldPreview() {
    WalletWizzardTheme {
        WizzardOtpField(onValueChange = {})
    }
}