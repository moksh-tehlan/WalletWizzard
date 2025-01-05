package com.moksh.presentation.ui.home_tab.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moksh.domain.usecases.savings.GetSavings
import com.moksh.domain.util.Result
import com.moksh.presentation.core.utils.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSavings: GetSavings,
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()
        .onStart { }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            HomeState()
        )

    private val _pocketState = MutableStateFlow(PocketState())
    val pocketState = _pocketState.asStateFlow()
        .onStart {
            fetchSavings()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            PocketState()
        )

    private fun fetchSavings() {
        viewModelScope.launch{
            _pocketState.update { it.copy(isLoading = true) }
            when (val result = getSavings.invoke()) {
                is Result.Success -> {
                    result.data.collect{ pocketList ->
                        _pocketState.update {
                            it.copy(pockets = pocketList, isLoading = false, error = null)
                        }
                    }
                }

                is Result.Error -> {
                    Timber.d(result.error.asUiText().toString())
                    _pocketState.update {
                        it.copy(error = result.error.asUiText(), isLoading = false)
                    }
                }
            }
        }
    }
}