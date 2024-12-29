package com.moksh.presentation.ui.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moksh.presentation.core.theme.forwardIcon
import com.moksh.presentation.ui.common.Gap
import com.moksh.presentation.ui.common.GapSpace

@Composable
fun AdditionalDataText(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    color: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(
                color = color
            )
        )
        Gap(size = GapSpace.MAX)
        Icon(
            modifier = Modifier.size(10.dp),
            imageVector = forwardIcon, contentDescription = "forward icon",
            tint = color
        )
        Icon(
            modifier = Modifier.size(10.dp),
            imageVector = forwardIcon, contentDescription = "forward icon",
            tint = color
        )
    }
}

@Composable
@Preview
private fun AdditionalDataTextPreview() {
    AdditionalDataText(text = "Additional Data", onClick = {})
}
