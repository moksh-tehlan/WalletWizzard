package com.moksh.presentation.ui.category

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.core.theme.WizzardBlue
import com.moksh.presentation.core.theme.WizzardWhite
import com.moksh.presentation.core.theme.addIcon
import com.moksh.presentation.core.theme.backArrowIcon
import com.moksh.presentation.ui.category.viewmodel.CategoryDataState
import com.moksh.presentation.ui.category.viewmodel.CategoryState
import com.moksh.presentation.ui.category.viewmodel.CategoryViewModel
import com.moksh.presentation.ui.common.Gap
import com.moksh.presentation.ui.common.GapSpace

@Composable
fun CategoryScreen(
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {
    CategoryScreenView(
        state = categoryViewModel.categoryState.collectAsStateWithLifecycle().value
    )
}

@Composable
fun CategoryScreenView(
    state: CategoryState,
) {
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
                    imageVector = backArrowIcon,
                    contentDescription = "Back Arrow",
                    tint = WizzardWhite
                )
                Gap(gapSpace = GapSpace.MAX)
                Text(
                    text = "Choose Category",
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
        when (state.categoryDataState) {
            is CategoryDataState.Error -> {
                Text(text = state.categoryDataState.error)
            }

            CategoryDataState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is CategoryDataState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(state.categoryDataState.categories.size) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(text = state.categoryDataState.categories[it].name)
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun CategoryComposablePreview() {
    WalletWizzardTheme {
        CategoryScreenView(
            state = CategoryState()
        )
    }
}