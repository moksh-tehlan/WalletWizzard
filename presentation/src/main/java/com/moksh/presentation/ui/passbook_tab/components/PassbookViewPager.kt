package com.moksh.presentation.ui.passbook_tab.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moksh.presentation.ui.common.Gap

@Composable
fun PassbookViewPager(
    modifier: Modifier = Modifier,
    amount: String,
    amountColor: Color,
    description: String,
    count: Int,
    content: @Composable (Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 180.dp, start = 10.dp, end = 10.dp, top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(
                        RoundedCornerShape(7.dp)
                    )
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Overview",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            MaterialTheme.colorScheme.onBackground.copy(.7f)
                        )
                    )
                    Gap(size = 20.dp)
                    Text(
                        text = buildAnnotatedString {
                            append("₹$amount")
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.onBackground.copy(0.4f),
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 12.sp
                                )
                            ) {
                                append("\n($description)")
                            }
                        }, style = MaterialTheme.typography.titleLarge.copy(
                            color = amountColor,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 20.sp
                        )
                    )
                }
            }
        }
        items(count = count) {
            content(it)
        }
    }
}