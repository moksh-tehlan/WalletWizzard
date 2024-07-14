package com.moksh.presentation.ui.books_tab.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BooksViewPager(
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

        }
        items(count = count) {
            content(it)
        }
    }
}