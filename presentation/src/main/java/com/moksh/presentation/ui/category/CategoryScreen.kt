package com.moksh.presentation.ui.category

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
import com.moksh.domain.model.response.Category
import com.moksh.domain.model.response.TransactionType
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.core.theme.WizzardBlue
import com.moksh.presentation.core.theme.WizzardWhite
import com.moksh.presentation.core.theme.addIcon
import com.moksh.presentation.core.theme.backArrowIcon
import com.moksh.presentation.ui.category.viewmodel.CategoryAction
import com.moksh.presentation.ui.category.viewmodel.CategoryDataState
import com.moksh.presentation.ui.category.viewmodel.CategoryEvent
import com.moksh.presentation.ui.category.viewmodel.CategoryState
import com.moksh.presentation.ui.category.viewmodel.CategoryViewModel
import com.moksh.presentation.ui.category.widget.AddNewCategoryBottomSheet
import com.moksh.presentation.ui.common.Gap
import com.moksh.presentation.ui.common.GapSpace
import com.moksh.presentation.ui.common.ObserveAsEvents
import com.moksh.presentation.ui.common.WalletWizzardCheckBox

@Composable
fun CategoryScreen(
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
    onCategorySaved: (Category?) -> Unit
) {
    ObserveAsEvents(flow = categoryViewModel.categoryEvent) { event ->
        when (event) {
            is CategoryEvent.OnBackPress -> onBackPress()
            is CategoryEvent.OnCategoryChanged -> onCategorySaved(event.category)
        }
    }
    CategoryScreenView(
        state = categoryViewModel.categoryState.collectAsStateWithLifecycle().value,
        onAction = categoryViewModel::onAction
    )
}

@Composable
fun CategoryScreenView(
    state: CategoryState,
    onAction: (CategoryAction) -> Unit = {},
) {
    if (state.addNewCategorySheet != null) {
        AddNewCategoryBottomSheet(
            value = state.addNewCategorySheet.value,
            onValueChange = { onAction(CategoryAction.NewCategoryChanged(it)) },
            onDismissRequest = { onAction(CategoryAction.ToggleAddNewCategorySheet) },
            isButtonLoading = state.addNewCategorySheet.isLoading,
            sheetState = rememberModalBottomSheetState(),
            onSave = { onAction(CategoryAction.SaveNewCategory) }
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
                            onAction(CategoryAction.OnBackPress)
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
                    .clickable {
                        onAction(CategoryAction.ToggleAddNewCategorySheet)
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
        when (state.categoryDataState) {
            is CategoryDataState.Error -> {
                Text(text = state.categoryDataState.error)
            }

            is CategoryDataState.Loading -> {
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
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    items(state.categoryDataState.categories) { category ->
                        Row(
                            modifier = Modifier
                                .background(
                                    WizzardWhite.copy(
                                        alpha = .05f
                                    )
                                )
                                .clickable {
                                    onAction(CategoryAction.CategoryChanged(category))

                                }
                                .padding(horizontal = 10.dp, vertical = 15.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            WalletWizzardCheckBox(
                                checked = state.selectedCategory == category,
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(text = category.name)
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
private fun CategoryComposablePreview() {
    WalletWizzardTheme {
        CategoryScreenView(
            state = CategoryState(
                categoryDataState = CategoryDataState.Success(
                    categories = List(
                        7,
                        init = {
                            Category(
                                id = "it",
                                name = "Category",
                                type = TransactionType.Income
                            )
                        },
                    )
                )
            )
        )
    }
}