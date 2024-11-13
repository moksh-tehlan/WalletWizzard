package com.moksh.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moksh.presentation.core.theme.WalletWizzardTheme

@Composable
fun WizzardTextField(
    modifier: Modifier = Modifier,
    value: String,
    hint: String? = null,
    heading: String? = null,
    onValueChange: (String) -> Unit,
    onClick:(()->Unit) ?= null,
    enabled: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(
        color = MaterialTheme.colorScheme.onBackground
    ),
    singleLine: Boolean = true,
    textFieldBackgroundColor: Color = MaterialTheme.colorScheme.surface,
    testTag: String? = null,
    cursorBrush: Brush = SolidColor(MaterialTheme.colorScheme.onBackground),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (!heading.isNullOrBlank()) {
            Text(
                text = heading,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground.copy(.7f)
                )
            )
            Gap(size = 6.dp)
        }
        BasicTextField(
            modifier = Modifier.semantics {
                if (testTag != null) this.testTag = testTag
            }.clickable { onClick?.invoke() },
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            enabled = enabled,
            textStyle = textStyle,
            cursorBrush = cursorBrush,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 55.dp, max = 100.dp)
                    .background(textFieldBackgroundColor)
                    .padding(horizontal = 14.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                it.invoke()
                if (value.isEmpty() && hint != null) {
                    Text(
                        text = hint,
                        style = textStyle.copy(
                            color = textStyle.color.copy(alpha = 0.1f),
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Clip
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun WizzardTextFieldPreview() {
    WalletWizzardTheme {
        WizzardTextField(
            value = "",
            hint = "Hint",
            heading = "Heading",
            onValueChange = {}
        )
    }
}
