package com.example.amphibians.ui.screen

import androidx.lifecycle.ViewModel
import com.example.amphibians.ui.Amphibian
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

sealed interface AmphibiansUiState {
    data class Success(val amphibians: List<Amphibian>): AmphibiansUiState
    class Error: AmphibiansUiState
    class Loading: AmphibiansUiState
}

class AmphibiansViewModel: ViewModel() {
    private val _uiState: MutableStateFlow<AmphibiansUiState> = MutableStateFlow(AmphibiansUiState.Loading())
    val uiState = _uiState.asStateFlow()

    private fun getAmphibiansData() {
        _uiState.value = AmphibiansUiState.Success(
            listOf(
                Amphibian("test", type = "test", description = "test", imgSrc = "test"),
                Amphibian("test", type = "test", description = "test", imgSrc = "test"),
                Amphibian("test", type = "test", description = "test", imgSrc = "test"),
                Amphibian("test", type = "test", description = "test", imgSrc = "test"),
                Amphibian("test", type = "test", description = "test", imgSrc = "test"),
                Amphibian("test", type = "test", description = "test", imgSrc = "test"),
                Amphibian("test", type = "test", description = "test", imgSrc = "test"),
            )
        )
    }

    init {
        getAmphibiansData()
    }
}