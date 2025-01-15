package com.moksh.presentation.ui.passbook_tab.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.core.theme.WizzardBlue
import com.moksh.presentation.core.theme.WizzardPurple
import com.moksh.presentation.ui.common.Gap

@Composable
fun PassbookItem(
    modifier: Modifier = Modifier,
    entryTime: String,
    remark: String,
    amount: String,
    amountColor: Color,
    paymentMode: String? = null,
    category: String? = null,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(3.dp))
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(20.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    category?.let {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(3.dp))
                                .background(
                                    WizzardPurple
                                )
                                .padding(5.dp)
                        ) {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    paymentMode?.let {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(3.dp))
                                .background(
                                    WizzardBlue
                                )
                                .padding(5.dp)
                        ) {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                    if(category != null || paymentMode != null){
                        Gap(size = 8.dp)
                    }
                    Text(
                        text = remark,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Text(
                    text = "₹$amount",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = amountColor
                    )
                )
            }
            Gap(size = 15.dp)
            HorizontalDivider(
                thickness = 1.dp
            )
            Gap(size = 10.dp)
            Text(
                text = buildAnnotatedString {
                    append("Entry at ")
                    withStyle(
                        style = SpanStyle(
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onBackground.copy(
                                0.5f
                            )
                        )
                    ) {
                        append(entryTime)
                    }
                },
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onBackground.copy(
                        .8f
                    )
                )
            )
        }
    }
}

@Composable
@Preview
fun PassbookItemPreview() {
    WalletWizzardTheme {
        PassbookItem(
            entryTime = "12:00",
            remark = "Salary",
            amount = "10000",
            amountColor = Color.Green,
        )
    }
}



